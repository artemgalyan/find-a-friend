package by.fpmibsu.find_a_friend.application;

import by.fpmibsu.find_a_friend.utils.Mediatr;
import by.fpmibsu.find_a_friend.utils.Request;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class EndpointsSender implements RequestPipeLineHandler {
    private final List<Endpoint<?, ?>> endpoints;
    private final Mediatr mediatr;

    public EndpointsSender(List<Endpoint<?, ?>> endpoints, Mediatr mediatr) {
        this.endpoints = endpoints;
        this.mediatr = mediatr;
    }

    @Override
    public void handle(HttpExchange exchange, RequestPipeLineHandler next) throws IOException {
        String method = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        var endpoint = endpoints.stream()
                .filter(e -> e.path().equals(uri.getPath()) && e.method().equals(method))
                .findFirst();
        if (!endpoint.isPresent()) {
            exchange.sendResponseHeaders(ResponseCodes.NOT_FOUND, 0);
            exchange.close();
            return;
        }

        var ep = endpoint.get();
        Class<?> requestType = ep.requestType();
        var mapper = new ObjectMapper();
        var bodyStream = exchange.getRequestBody();
        var body = new BufferedReader(new InputStreamReader(bodyStream)).lines().collect(Collectors.joining("\n"));
        Request<?> request;
        try {
            request = (Request<?>) tryCreateNewInstance(body, requestType, mapper);
        } catch (Exception e) {
            exchange.sendResponseHeaders(ResponseCodes.BAD_REQUEST, 0);
            exchange.close();
            return;
        }
        Object response = mediatr.send(request);
        var responseAsString = mapper.writeValueAsString(response);
        var bytes = responseAsString.getBytes();
        exchange.sendResponseHeaders(ResponseCodes.OK, bytes.length);
        OutputStream out = exchange.getResponseBody();
        out.write(bytes);
        out.close();
        exchange.close();
    }

    private <T> T tryCreateNewInstance(String body, Class<T> clazz, ObjectMapper mapper) throws Exception {
        if (body.isEmpty()) {
            return (T) clazz.getConstructors()[0].newInstance();
        }
        return mapper.readValue(body, clazz);
    }
}
