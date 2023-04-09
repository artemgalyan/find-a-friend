package by.fpmibsu.find_a_friend.application.requestpipeline;

import by.fpmibsu.find_a_friend.application.serviceproviders.ScopedServiceProvider;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface RequestPipeLineHandler {
    void handle(HttpExchange exchange, ScopedServiceProvider serviceProvider, RequestPipeLineHandler next) throws IOException;
}
