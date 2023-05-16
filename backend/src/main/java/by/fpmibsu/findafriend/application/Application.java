package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.controller.EndpointInfo;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.findafriend.controller.ServletUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.Optional;

public class Application {
    private final GlobalServiceProvider globalServiceProvider;
    private final List<EndpointInfo> endpointInfos;

    public Application(GlobalServiceProvider globalServiceProvider, List<EndpointInfo> endpointInfos) {
        this.globalServiceProvider = globalServiceProvider;
        this.endpointInfos = endpointInfos;
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

        HandleResult result = ControllerMethodInvoker.invoke(
                request, response, endpoint.get(),
                globalServiceProvider.getRequestServiceProvider()
        );
        response.setStatus(result.getCode());
        if (result.getResponseObject().isPresent()) {
            ServletUtils.writeResponse(result.getResponseObject().get(), response.getOutputStream());
        }
    }
}
