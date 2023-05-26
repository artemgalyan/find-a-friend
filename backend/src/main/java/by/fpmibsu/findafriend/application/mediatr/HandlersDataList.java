package by.fpmibsu.findafriend.application.mediatr;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class HandlersDataList {
    private final Logger logger = LogManager.getLogger();

    private static class RequestHandlerInfo<T, R extends Request<? super T>> {
        Class<? extends RequestHandler<T, R>> clazz;
        Class<?> requestType;

        public RequestHandlerInfo(Class<? extends RequestHandler<T, R>> clazz, Class<R> requestType) {
            this.clazz = clazz;
            this.requestType = requestType;
        }
    }

    private final List<RequestHandlerInfo<?, ?>> handlers = new ArrayList<>();

    public <T, R extends Request<? super T>> void registerHandler(Class<? extends RequestHandler<T, R>> clazz, Class<R> requestType) {
        if (handlers.stream().anyMatch(h -> h.clazz.equals(clazz) || h.requestType.equals(requestType))) {
            logger.error("A handler of this type/for this request is already registered");
            throw new IllegalArgumentException("A handler of this type/for this request is already registered");
        }

        handlers.add(new RequestHandlerInfo<>(clazz, requestType));
    }

    public Optional<Class<? extends RequestHandler<?, ?>>> findHandler(Class<?> requestClazz) {
        var handler = handlers.stream()
                .filter(h -> h.requestType.equals(requestClazz))
                .findFirst();
        return handler.map(requestHandlerInfo -> requestHandlerInfo.clazz);
    }
}
