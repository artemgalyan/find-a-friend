package by.fpmibsu.find_a_friend.application.endpoints;

import by.fpmibsu.find_a_friend.application.ResponseCodes;
import by.fpmibsu.find_a_friend.application.mediatr.Mediatr;
import by.fpmibsu.find_a_friend.application.requestpipeline.RequestPipeLineHandler;
import by.fpmibsu.find_a_friend.application.serviceproviders.ScopedServiceProvider;
import by.fpmibsu.find_a_friend.application.serviceproviders.ServiceProvider;
import by.fpmibsu.find_a_friend.services.Request;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

public class EndpointsSender implements RequestPipeLineHandler {
    private final List<EndpointInfo<?, ?>> endpointInfos;

    public EndpointsSender(List<EndpointInfo<?, ?>> endpointInfos) {
        this.endpointInfos = endpointInfos;
    }

    @Override
    public void handle(HttpExchange exchange, ScopedServiceProvider serviceProvider, RequestPipeLineHandler next) throws IOException {
        exchange.getResponseHeaders().set("Content-Type", "application/json; charset=UTF-8");
        String method = exchange.getRequestMethod();
        URI uri = exchange.getRequestURI();
        var endpoint = endpointInfos.stream()
                .filter(e -> e.path().equals(uri.getPath()) && e.method().name().equals(method))
                .findFirst();
        if (endpoint.isEmpty()) {
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
        var mediatr = serviceProvider.getRequiredService(Mediatr.class);
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
