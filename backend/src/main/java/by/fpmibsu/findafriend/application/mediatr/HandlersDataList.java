package by.fpmibsu.findafriend.application.mediatr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.*;


public class HandlersDataList {
    private final Logger logger = LogManager.getLogger(HandlersDataList.class);

    private static class RequestHandlerInfo<T, R extends Request<? super T>> {
        Class<? extends RequestHandler<T, R>> clazz;
        Class<?> requestType;

        public RequestHandlerInfo(Class<? extends RequestHandler<T, R>> clazz, Class<R> requestType) {
            this.clazz = clazz;
            this.requestType = requestType;
        }
    }

    private final Map<Class<? extends Request<?>>, RequestHandlerInfo<?, ?>> handlers = new HashMap<>();

    public <T, R extends Request<? super T>> void registerHandler(Class<? extends RequestHandler<T, R>> clazz, Class<R> requestType) {
        if (handlers.containsKey(requestType)) {
            logger.fatal(String.format("A handler for request type %s is already registered", requestType.getName()));
            throw new IllegalArgumentException("A handler of this type/for this request is already registered");
        }

        handlers.put(requestType, new RequestHandlerInfo<>(clazz, requestType));
    }

    public Optional<Class<? extends RequestHandler<?, ?>>> findHandler(Class<?> requestClazz) {
        RequestHandlerInfo<?, ?> info = handlers.get(requestClazz);
        if (info == null) {
            return Optional.empty();
        }
        return Optional.of(info.clazz);
    }
}
