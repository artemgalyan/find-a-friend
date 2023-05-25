package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.controller.EndpointInfo;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.findafriend.controller.ServletUtils;
import by.fpmibsu.findafriend.services.JwtSigner;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Optional;

public class Application {
    public record AuthenticationData(JwtClaims claims, boolean isTokenValid) {}

    public record Keys(RSAPublicKey publicKey, RSAPrivateKey privateKey) {}

    private final GlobalServiceProvider globalServiceProvider;
    private final List<EndpointInfo> endpointInfos;
    private final JwtConsumer consumer;


    public Application(GlobalServiceProvider globalServiceProvider, List<EndpointInfo> endpointInfos, Keys keys) {
        this.globalServiceProvider = globalServiceProvider;
        this.endpointInfos = endpointInfos;
        consumer = new JwtConsumerBuilder()
//                .setRequireExpirationTime()
//                .setRequireSubject()
                .setVerificationKey(keys.publicKey())
                .setJwsAlgorithmConstraints(
                        AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256)
                .build();
        globalServiceProvider.addSingleton(JwtSigner.class, () -> new JwtSigner(keys.privateKey()));
    }

    public void send(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletUtils.setResponseHeaders(response);
        Optional<EndpointInfo> endpoint = endpointInfos.stream()
                .filter(e -> e.path().equals(request.getPathInfo())
                        && e.httpMethod().toString().equalsIgnoreCase(request.getMethod()))
                .findFirst();

        if (endpoint.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        var sp = globalServiceProvider.getRequestServiceProvider();
        var claims = parseToken(request.getParameter("token"));
        sp.addScoped(AuthenticationData.class, claims);
        HandleResult result = ControllerMethodInvoker.invoke(
                request, response, endpoint.get(), sp
        );
        response.setStatus(result.getCode());
        if (result.getResponseObject().isPresent()) {
            ServletUtils.writeResponse(result.getResponseObject().get(), response.getOutputStream());
        }
    }

    private AuthenticationData parseToken(String token) {
        try {
            return new AuthenticationData(consumer.processToClaims(token), true);
        } catch (Exception e) {
            return new AuthenticationData(new JwtClaims(), false);
        }
    }
}
