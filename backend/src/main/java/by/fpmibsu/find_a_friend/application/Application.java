package by.fpmibsu.find_a_friend.application;

import by.fpmibsu.find_a_friend.services.DIContainer;
import by.fpmibsu.find_a_friend.utils.Mediatr;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;

public class Application {
    private HttpServer httpServer;
    private List<Endpoint<?, ?>> endpoints;
    private DIContainer diContainer;
    private Mediatr mediatr;
    private List<RequestPipeLineHandler> pipeLineHandlers;

    public Application(InetSocketAddress address, int backlog, List<Endpoint<?, ?>> endpoints, DIContainer diContainer, List<RequestPipeLineHandler> pipeLineHandlers) {
        this.mediatr = new Mediatr(diContainer);
        this.pipeLineHandlers = pipeLineHandlers;
        try {
            this.httpServer = HttpServer.create(address, backlog);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        this.endpoints = endpoints;
        this.diContainer = diContainer;
        endpoints.forEach(this::configureEndpoint);
    }

    private void configureEndpoint(Endpoint<?, ?> endpoint) {
        httpServer.createContext(endpoint.path(), new HttpRequestPipeLineHandler(pipeLineHandlers));
    }

    public void start() {
        httpServer.setExecutor(null);
        httpServer.start();
    }
}
