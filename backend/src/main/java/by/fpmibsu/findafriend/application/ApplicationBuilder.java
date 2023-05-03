package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.controller.Controller;
import by.fpmibsu.findafriend.application.controller.ControllerMapper;
import by.fpmibsu.findafriend.application.mediatr.HandlersDataList;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.application.serviceproviders.DefaultGlobalServiceProvider;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;

public class ApplicationBuilder {
    private final GlobalServiceProvider serviceProvider = new DefaultGlobalServiceProvider();
    private final HandlersDataList handlersDataList = new HandlersDataList();

    public ApplicationBuilder() {
        serviceProvider.addService(HandlersDataList.class, GlobalServiceProvider.ServiceType.SINGLETON, () -> handlersDataList);
        serviceProvider.addScoped(Mediatr.class);
    }

    public Application build() {
        /// TODO: Передать туда список
        return new Application(serviceProvider, null);
    }

    public GlobalServiceProvider services() {
        return serviceProvider;
    }

    public <T, R extends Request<? super T>> ApplicationBuilder registerHandler(Class<? extends RequestHandler<T, R>> clazz, Class<R> requestType) {
        handlersDataList.registerHandler(clazz, requestType);
        return this;
    }

    public GlobalServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public ApplicationBuilder mapController(Class<? extends Controller> controller) {
        /// TODO:
        var data = ControllerMapper.mapController(controller);
        /// добавить дату в список, потом передать его объекту приложения
        return this;
    }
}
