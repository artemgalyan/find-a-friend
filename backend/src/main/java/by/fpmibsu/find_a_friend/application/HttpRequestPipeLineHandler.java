package by.fpmibsu.find_a_friend.application;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.util.List;

public class HttpRequestPipeLineHandler implements HttpHandler {
    private final List<RequestPipeLineHandler> handlers;

    public HttpRequestPipeLineHandler(List<RequestPipeLineHandler> handlers) {
        this.handlers = handlers;
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        handlers.get(0).handle(exchange, new NextPipeLineHandler());
    }

    private class NextPipeLineHandler implements RequestPipeLineHandler {
        private int index = 0;

        @Override
        public void handle(HttpExchange exchange, RequestPipeLineHandler next) throws IOException {
            ++index;
            handlers.get(index).handle(exchange, this);
        }
    }
}
