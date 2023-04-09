package by.fpmibsu.find_a_friend.application.requestpipeline;

import by.fpmibsu.find_a_friend.application.serviceproviders.ServiceProvider;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface RequestPipeLineHandler {
    void handle(HttpExchange exchange, ServiceProvider serviceProvider, RequestPipeLineHandler next) throws IOException;
}
