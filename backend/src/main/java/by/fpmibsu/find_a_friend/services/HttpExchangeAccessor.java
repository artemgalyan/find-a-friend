package by.fpmibsu.find_a_friend.services;

import by.fpmibsu.find_a_friend.application.requestpipeline.RequestPipeLineHandler;
import by.fpmibsu.find_a_friend.application.serviceproviders.ScopedServiceProvider;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class HttpExchangeAccessor implements RequestPipeLineHandler {
    @Override
    public void handle(HttpExchange exchange, ScopedServiceProvider serviceProvider, RequestPipeLineHandler next) throws IOException {
        serviceProvider.addScoped(HttpExchange.class, exchange);
        next.handle(exchange, serviceProvider, next);
    }
}
