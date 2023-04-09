package by.fpmibsu.find_a_friend.application.mediatr;

import by.fpmibsu.find_a_friend.services.Request;
import by.fpmibsu.find_a_friend.services.RequestHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class HandlersDataList {
    private static class RequestHandlerInfo<T, R extends Request<? super T>> {
        Class<? extends RequestHandler<T, R>> clazz;
        Class<?> requestType;
        Class<?> resultType;

        public RequestHandlerInfo(Class<? extends RequestHandler<T, R>> clazz, Class<T> resultType, Class<R> requestType) {
            this.clazz = clazz;
            this.requestType = requestType;
            this.resultType = resultType;
        }
    }

    private final List<RequestHandlerInfo<?, ?>> handlers = new ArrayList<>();

    public <T, R extends Request<? super T>> void registerHandler(Class<? extends RequestHandler<T, R>> clazz, Class<T> resultType, Class<R> requestType) {
        if (handlers.stream().anyMatch(h -> h.clazz.equals(clazz) || h.requestType.equals(requestType))) {
            throw new IllegalArgumentException("A handler of this type/for this request is already registered");
        }

        handlers.add(new RequestHandlerInfo<>(clazz, resultType, requestType));
    }

    public Optional<Class<? extends RequestHandler<?, ?>>> findHandler(Class<?> requestClazz) {
        var handler = handlers.stream()
                .filter(h -> h.requestType.equals(requestClazz))
                .findFirst();
        return handler.map(requestHandlerInfo -> requestHandlerInfo.clazz);
    }
}
