package by.fpmibsu.find_a_friend.application.endpoints;

import by.fpmibsu.find_a_friend.application.ResponseCodes;
import by.fpmibsu.find_a_friend.application.mediatr.Mediatr;
import by.fpmibsu.find_a_friend.application.requestpipeline.RequestPipeLineHandler;
import by.fpmibsu.find_a_friend.application.serviceproviders.ScopedServiceProvider;
import by.fpmibsu.find_a_friend.application.mediatr.Request;
import by.fpmibsu.find_a_friend.application.utils.Mapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sun.net.httpserver.HttpExchange;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
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
                .filter(e -> e.path().equals(uri.getPath().split("\\?")[0]) && e.method().name().equals(method))
                .findFirst();
        if (endpoint.isEmpty()) {
            exchange.sendResponseHeaders(ResponseCodes.NOT_FOUND, 0);
            exchange.close();
            return;
        }

        var ep = endpoint.get();
        Class<?> requestType = ep.requestType();
//        var readType = ep.requestData();

        var mapper = new ObjectMapper();
        var bodyStream = exchange.getRequestBody();
        var body = new BufferedReader(new InputStreamReader(bodyStream)).lines().collect(Collectors.joining("\n"));
        Request<?> request;
        try {
            request = (Request<?>) tryCreateNewInstance(body, requestType, mapper);//readType == RequestData.FROM_BODY
                    //? (Request<?>) tryCreateNewInstance(body, requestType, mapper)
                    //: (Request<?>) readFromQuery(requestType, uri.getPath().split("\\?")[1]);
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

    private <T> T readFromQuery(Class<T> clazz, String query) throws Exception {
        var q = query.split("&");
        var instance = (T) clazz.getConstructors()[0].newInstance();
        for (int i = 0; i < q.length; ++i) {
            var fields = q[i].split("=");
            var name = fields[0];
            var value = fields[1];
            var field = clazz.getField(name);
            if (field == null) {
                continue;
            }

            var type = field.getType();
            var mapped = Mapper.map(value, type);
            if (!type.isPrimitive()) {
                field.set(instance, mapped);
                continue;
            }

            if (mapped instanceof Integer in) {
                field.setInt(instance, in);
            } else if (mapped instanceof Long l) {
                field.setLong(instance, l);
            } else if (mapped instanceof Boolean b) {
                field.setBoolean(instance, b);
            } else if (mapped instanceof Byte by) {
                field.setByte(instance, by);
            } else if (mapped instanceof Short s) {
                field.setShort(instance, s);
            } else if (mapped instanceof Double d) {
                field.setDouble(instance, d);
            } else if (mapped instanceof Float fl) {
                field.setFloat(instance, fl);
            } else if (mapped instanceof Character c) {
                field.setChar(instance, c);
            }
        }
        return instance;
    }
}
