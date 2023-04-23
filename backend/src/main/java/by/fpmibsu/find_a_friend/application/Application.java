package by.fpmibsu.find_a_friend.application;

import by.fpmibsu.find_a_friend.application.endpoints.EndpointInfo;
import by.fpmibsu.find_a_friend.application.mediatr.HandlersDataList;
import by.fpmibsu.find_a_friend.application.requestpipeline.HttpRequestPipeLineHandler;
import by.fpmibsu.find_a_friend.application.requestpipeline.RequestPipeLineHandler;
import by.fpmibsu.find_a_friend.application.serviceproviders.GlobalServiceProvider;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class Application {
    private final HttpServer httpServer;
    private final GlobalServiceProvider globalServiceProvider;
    private final List<RequestPipeLineHandler> pipeLineHandlers;

    public Application(InetSocketAddress address, int backlog, List<EndpointInfo<?, ?>> endpointInfos,
                       GlobalServiceProvider globalServiceProvider, List<RequestPipeLineHandler> pipeLineHandlers,
                       HandlersDataList handlersDataList) {
        this.pipeLineHandlers = pipeLineHandlers;
        try {
            this.httpServer = HttpServer.create(address, backlog);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.globalServiceProvider = globalServiceProvider;
        endpointInfos.forEach(this::configureEndpoint);
    }

    private void configureEndpoint(EndpointInfo<?, ?> endpointInfo) {
        httpServer.createContext(endpointInfo.path(), new HttpRequestPipeLineHandler(pipeLineHandlers, globalServiceProvider));
    }

    public void start() {
        httpServer.setExecutor(null);
        httpServer.start();
    }
}
