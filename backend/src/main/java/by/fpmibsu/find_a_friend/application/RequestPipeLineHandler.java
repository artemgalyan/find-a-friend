package by.fpmibsu.find_a_friend.application;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public interface RequestPipeLineHandler {
    void handle(HttpExchange exchange, RequestPipeLineHandler next) throws IOException;
}
