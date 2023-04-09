package by.fpmibsu.find_a_friend.application.requestpipeline;

import by.fpmibsu.find_a_friend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.find_a_friend.application.serviceproviders.ScopedServiceProvider;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;

public class HttpRequestPipeLineHandler implements HttpHandler {
    private final List<RequestPipeLineHandler> handlers;
    private final GlobalServiceProvider serviceProvider;

    public HttpRequestPipeLineHandler(List<RequestPipeLineHandler> handlers, GlobalServiceProvider serviceProvider) {
        this.handlers = handlers;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        handlers.get(0).handle(exchange, serviceProvider.getRequestServiceProvider(), new NextPipeLineHandler());
    }

    private class NextPipeLineHandler implements RequestPipeLineHandler {
        private int index = 0;

        @Override
        public void handle(HttpExchange exchange, ScopedServiceProvider serviceProvider, RequestPipeLineHandler next) throws IOException {
            ++index;
            handlers.get(index).handle(exchange, serviceProvider, this);
        }
    }
}
