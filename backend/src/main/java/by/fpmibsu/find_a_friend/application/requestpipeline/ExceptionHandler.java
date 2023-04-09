package by.fpmibsu.find_a_friend.application.requestpipeline;

import by.fpmibsu.find_a_friend.application.serviceproviders.ScopedServiceProvider;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

import static by.fpmibsu.find_a_friend.application.ResponseCodes.INTERNAL_SERVER_ERROR;

public class ExceptionHandler implements RequestPipeLineHandler {
    @Override
    public void handle(HttpExchange exchange, ScopedServiceProvider serviceProvider, RequestPipeLineHandler next) throws IOException {
        try {
            next.handle(exchange, serviceProvider, next);
        } catch (Exception e) {
            e.printStackTrace();
            exchange.sendResponseHeaders(INTERNAL_SERVER_ERROR, -1);
            exchange.close();
        }
    }
}
