package by.fpmibsu.find_a_friend.utils;

import by.fpmibsu.find_a_friend.services.DIContainer;

import java.util.ArrayList;
import java.util.List;

public class Mediatr {
    public Mediatr(DIContainer diContainer) {
        this.diContainer = diContainer;
    }

    private static class RequestHandlerInfo<T extends Request, R extends Result<? extends T>> {
        Class<? extends RequestHandler<T, R>> clazz;
        Class<?> requestType;
        Class<?> resultType;

        public RequestHandlerInfo(Class<? extends RequestHandler<T, R>> clazz, Class<T> requestType, Class<R> resultType) {
            this.clazz = clazz;
            this.requestType = requestType;
            this.resultType = resultType;
        }
    }

    private final DIContainer diContainer;
    private final List<RequestHandlerInfo<?, ?>> handlers = new ArrayList<>();

    public <T extends Request, R extends Result<? extends T>> void registerHandler(Class<? extends RequestHandler<T, R>> clazz, Class<T> requestType, Class<R> resultType) {
        if (handlers.stream().anyMatch(h -> h.clazz.equals(clazz) || h.requestType.equals(requestType))) {
            throw new IllegalArgumentException("A handler of this type/for this request is already registered");
        }

        handlers.add(new RequestHandlerInfo<>(clazz, requestType, resultType));
    }

    public <R extends Request> Result<? extends R> send(R request) {
        var clazz = request.getClass();
        var handler = handlers.stream()
                .filter(h -> h.requestType.equals(clazz))
                .findFirst();
        if (handler.isEmpty()) {
            throw new NoHandlerException("There is no handler for type " + clazz.getName());
        }

        RequestHandler<R, ?> handlerInstance = (RequestHandler<R, ?>) diContainer.createObject(handler.get().clazz);
        return handlerInstance.handle(request);
    }
}
