package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.controller.EndpointInfo;
import by.fpmibsu.findafriend.application.requestpipeline.PipelineHandler;
import by.fpmibsu.findafriend.application.requestpipeline.PipelineSender;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.findafriend.controller.ServletUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.List;
import java.util.Map;

public class Application {
    private static final Logger logger = LogManager.getLogger(Application.class);

    public record Keys(RSAPublicKey publicKey, RSAPrivateKey privateKey) {
    }

    private final GlobalServiceProvider globalServiceProvider;
    private final Map<String, EndpointInfo> endpointInfos;
    private final List<PipelineHandler> pipelineHandlers;



    public Application(GlobalServiceProvider globalServiceProvider, Map<String, EndpointInfo> endpointInfos, Keys keys, List<PipelineHandler> pipelineHandlers) {
        this.globalServiceProvider = globalServiceProvider;
        this.endpointInfos = endpointInfos;
        this.pipelineHandlers = pipelineHandlers;
    }

    public void send(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.trace("Started request " + request.getContextPath() + " processing from " + request.getLocalAddr());
        ServletUtils.setResponseHeaders(response);
        if (!endpointInfos.containsKey(request.getPathInfo())) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }

        EndpointInfo endpoint = endpointInfos.get(request.getPathInfo());
        var sp = globalServiceProvider.getRequestServiceProvider();
        var pipeline = new PipelineSender(pipelineHandlers);
        HandleResult result;
        try {
            result = pipeline.handle(request, response, sp, endpoint, null);
        } catch (Exception e) {
            logger.error(String.format("An exception occurred during request processing %s: ", request.getPathInfo()) + e);
            result = new HandleResult(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
        response.setStatus(result.getCode());
        if (result.getResponseObject().isPresent()) {
            ServletUtils.writeResponse(result.getResponseObject().get(), response.getOutputStream());
        }
        logger.trace("Finished request " + request.getContextPath() + " processing from " + request.getLocalAddr());
    }
}
