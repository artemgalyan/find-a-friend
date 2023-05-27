package by.fpmibsu.findafriend.application.mediatr;

import by.fpmibsu.findafriend.application.serviceproviders.ServiceProvider;
import by.fpmibsu.findafriend.application.utils.ObjectConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Mediatr {
    private static final Logger logger = LogManager.getLogger(Mediatr.class);

    public Mediatr(ServiceProvider serviceProvider, HandlersDataList handlersDataList) {
        this.serviceProvider = serviceProvider;
        this.handlersDataList = handlersDataList;
    }

    private final ServiceProvider serviceProvider;
    private final HandlersDataList handlersDataList;

    public <T, R extends Request<? super T>> T send(R request) {
        logger.trace(String.format("Handling request of type %s", request.getClass().getName()));
        var clazz = request.getClass();
        var handler = handlersDataList.findHandler(request.getClass());
        if (handler.isEmpty()) {
            logger.error("There is no handler for type " + clazz.getName());
            throw new NoHandlerException("There is no handler for type " + clazz.getName());
        }
        RequestHandler<?, R> handlerInstance = (RequestHandler<?, R>) ObjectConstructor.createInstance(handler.get(), serviceProvider);
        try {
            var start = System.currentTimeMillis();
            var result = (T) handlerInstance.handle(request);
            var end = System.currentTimeMillis();
            logger.info(String.format("Handled request of type %s in time: %d ms", request.getClass().getName(), end - start));
            return result;
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
