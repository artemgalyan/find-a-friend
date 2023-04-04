package by.fpmibsu.find_a_friend.utils;

import by.fpmibsu.find_a_friend.services.DIContainer;

import java.util.ArrayList;
import java.util.List;

public class Mediatr {
    public Mediatr(DIContainer diContainer) {
        this.diContainer = diContainer;
    }

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

    private final DIContainer diContainer;
    private final List<RequestHandlerInfo<?, ?>> handlers = new ArrayList<>();

    public <T, R extends Request<? super T>> void registerHandler(Class<? extends RequestHandler<T, R>> clazz, Class<T> resultType, Class<R> requestType) {
        if (handlers.stream().anyMatch(h -> h.clazz.equals(clazz) || h.requestType.equals(requestType))) {
            throw new IllegalArgumentException("A handler of this type/for this request is already registered");
        }

        handlers.add(new RequestHandlerInfo<>(clazz, resultType, requestType));
    }

    public <T, R extends Request<? super T>> T send(R request) {
        var clazz = request.getClass();
        var handler = handlers.stream()
                .filter(h -> h.requestType.equals(clazz))
                .findFirst();
        if (handler.isEmpty()) {
            throw new NoHandlerException("There is no handler for type " + clazz.getName());
        }

        RequestHandler<?, R> handlerInstance = (RequestHandler<?, R>) diContainer.createObject(handler.get().clazz);
        return (T) handlerInstance.handle(request);
    }
}
