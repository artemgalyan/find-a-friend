package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.authentication.AuthenticationData;
import by.fpmibsu.findafriend.application.authentication.JwtSigner;
import by.fpmibsu.findafriend.application.controller.Controller;
import by.fpmibsu.findafriend.application.controller.ControllerMapper;
import by.fpmibsu.findafriend.application.controller.EndpointInfo;
import by.fpmibsu.findafriend.application.mediatr.HandlersDataList;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.application.requestpipeline.PipelineHandler;
import by.fpmibsu.findafriend.application.serviceproviders.DefaultGlobalServiceProvider;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.jwa.AlgorithmConstraints;
import org.jose4j.jws.AlgorithmIdentifiers;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.jwt.consumer.JwtConsumer;
import org.jose4j.jwt.consumer.JwtConsumerBuilder;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationBuilder {
    private final Logger logger = LogManager.getLogger(Application.class);
    private final GlobalServiceProvider serviceProvider = new DefaultGlobalServiceProvider();
    private final HandlersDataList handlersDataList = new HandlersDataList();
    private final Map<String, EndpointInfo> endpointInfos = new HashMap<>();
    private final List<PipelineHandler> pipelineHandlers = new ArrayList<>();
    private Application.Keys keys;

    public ApplicationBuilder() {
        serviceProvider.addService(HandlersDataList.class, GlobalServiceProvider.ServiceType.SINGLETON, () -> handlersDataList);
        serviceProvider.addScoped(Mediatr.class);
        addPipelineHandler((request, response, scopedServiceProvider, endpointInfo, next) -> {
            long start = System.currentTimeMillis();
            try {
                HandleResult result = next.handle(request, response, scopedServiceProvider, endpointInfo, null);
                long end = System.currentTimeMillis();
                logger.info(String.format("Handled request %s in %d ms", request.getPathInfo(), end - start));
                return result;
            } catch (Exception e) {
                logger.error(e);
                return new HandleResult(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        });
    }

    public Application build() {
        pipelineHandlers.add(ControllerMethodInvoker.asPipelineHandler());
        return new Application(serviceProvider, endpointInfos, keys, pipelineHandlers);
    }

    public ApplicationBuilder readKeys(String path) {
        try {
            var input = new ObjectInputStream(
                    new FileInputStream(path + "public")
            );
            var publicKey = (RSAPublicKey) input.readObject();
            input.close();
            input = new ObjectInputStream(
                    new FileInputStream(path + "private")
            );
            var privateKey = (RSAPrivateKey) input.readObject();
            input.close();
            keys = new Application.Keys(publicKey, privateKey);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public GlobalServiceProvider services() {
        return serviceProvider;
    }

    public <T, R extends Request<? super T>> ApplicationBuilder registerHandler(Class<? extends RequestHandler<T, R>> clazz, Class<R> requestType) {
        handlersDataList.registerHandler(clazz, requestType);
        return this;
    }

    public GlobalServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public ApplicationBuilder mapController(Class<? extends Controller> controller) {
        List<EndpointInfo> data = ControllerMapper.mapController(controller);
        data.forEach(d -> endpointInfos.put(d.path(), d));
        return this;
    }

    public ApplicationBuilder addPipelineHandler(PipelineHandler handler) {
        pipelineHandlers.add(handler);
        return this;
    }

    public ApplicationBuilder addAuthentication() {
        var consumer = new JwtConsumerBuilder()
                .setRequireExpirationTime()
                .setVerificationKey(keys.publicKey())
                .setJwsAlgorithmConstraints(
                        AlgorithmConstraints.ConstraintType.PERMIT, AlgorithmIdentifiers.RSA_USING_SHA256)
                .build();
        return addAuthentication(consumer);
    }

    public ApplicationBuilder addAuthentication(JwtConsumer consumer) {
        pipelineHandlers.add((request, response, scopedServiceProvider, endpointInfo, next) -> {
            AuthenticationData authData;
            var token = request.getParameter("token");
            try {
                authData = new AuthenticationData(consumer.processToClaims(token), true, token);
            } catch (Exception e) {
                authData = new AuthenticationData(new JwtClaims(), false, token);
            }
            scopedServiceProvider.addScoped(AuthenticationData.class, authData);
            return next.handle(request, response, scopedServiceProvider, endpointInfo, null);
        });
        serviceProvider.addSingleton(JwtSigner.class, () -> new JwtSigner(keys.privateKey()));
        return this;
    }
}
