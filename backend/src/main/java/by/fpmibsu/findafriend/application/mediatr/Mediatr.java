package by.fpmibsu.findafriend.application.mediatr;

import by.fpmibsu.findafriend.application.serviceproviders.ServiceProvider;
import by.fpmibsu.findafriend.application.utils.ObjectConstructor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class Mediatr {
    public Mediatr(ServiceProvider serviceProvider, HandlersDataList handlersDataList) {
        this.serviceProvider = serviceProvider;
        this.handlersDataList = handlersDataList;
    }

    private final ServiceProvider serviceProvider;
    private final HandlersDataList handlersDataList;
    private final Logger logger = LogManager.getLogger();

    public <T, R extends Request<? super T>> T send(R request) {
        var clazz = request.getClass();
        var handler = handlersDataList.findHandler(request.getClass());
        if (handler.isEmpty()) {
            logger.error("There is no handler for type " + clazz.getName());
            throw new NoHandlerException("There is no handler for type " + clazz.getName());
        }

        RequestHandler<?, R> handlerInstance = (RequestHandler<?, R>) ObjectConstructor.createInstance(handler.get(), serviceProvider);
        try {
            return (T) handlerInstance.handle(request);
        } catch (Exception e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
