package by.fpmibsu.find_a_friend.application;

import by.fpmibsu.find_a_friend.application.controllers.Controller;
import by.fpmibsu.find_a_friend.application.controllers.ControllerMapper;
import by.fpmibsu.find_a_friend.application.endpoints.EndpointInfo;
import by.fpmibsu.find_a_friend.application.endpoints.EndpointsSender;
import by.fpmibsu.find_a_friend.application.mediatr.HandlersDataList;
import by.fpmibsu.find_a_friend.application.mediatr.Mediatr;
import by.fpmibsu.find_a_friend.application.requestpipeline.ExceptionHandler;
import by.fpmibsu.find_a_friend.application.requestpipeline.RequestPipeLineHandler;
import by.fpmibsu.find_a_friend.application.serviceproviders.DefaultGlobalServiceProvider;
import by.fpmibsu.find_a_friend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.find_a_friend.application.serviceproviders.ServiceProvider;
import by.fpmibsu.find_a_friend.services.Request;
import by.fpmibsu.find_a_friend.services.RequestHandler;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ApplicationBuilder {
    private final GlobalServiceProvider serviceProvider = new DefaultGlobalServiceProvider();
    private final HandlersDataList handlersDataList = new HandlersDataList();
    private final List<EndpointInfo<?, ?>> endpointInfos = new ArrayList<>();
    private final List<RequestPipeLineHandler> pipeLineHandlers = new ArrayList<>();
    private int port = 8080;
    private int backlog = 0;

    public ApplicationBuilder() {
        pipeLineHandlers.add(new ExceptionHandler());
        serviceProvider.addService(HandlersDataList.class, GlobalServiceProvider.ServiceType.SINGLETON, () -> handlersDataList);
        serviceProvider.addService(Mediatr.class, GlobalServiceProvider.ServiceType.SCOPED);
    }

    public Application build() {
        pipeLineHandlers.add(new EndpointsSender(endpointInfos));
        return new Application(new InetSocketAddress(port), 0, endpointInfos, serviceProvider, pipeLineHandlers, handlersDataList);
    }

    public GlobalServiceProvider services() {
        return serviceProvider;
    }

    public ApplicationBuilder mapController(Class<? extends Controller> controller) {
        List<EndpointInfo> endpoints = ControllerMapper.mapController(controller); // raw use to avoid compilation errors
        endpoints.forEach(this::addEndpoint);
        return this;
    }

    public ApplicationBuilder addPipeLineHandler(RequestPipeLineHandler handler) {
        pipeLineHandlers.add(handler);
        return this;
    }

    private <T, R extends Request<? super T>> ApplicationBuilder addEndpoint(EndpointInfo<T, R> endpointInfo) {
        if (endpointInfos.stream().anyMatch(e -> e.path().equals(endpointInfo.path()) && e.method().equals(endpointInfo.method()))) {
            throw new DuplicateEndpointException();
        }

        registerHandler(endpointInfo.handlerType(), endpointInfo.responseType(), endpointInfo.requestType());
        endpointInfos.add(endpointInfo);
        return this;
    }

    private <T, R extends Request<? super T>> void registerHandler(Class<? extends RequestHandler<T, R>> clazz, Class<T> resultType, Class<R> requestType) {
        handlersDataList.registerHandler(clazz, resultType, requestType);
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public int getBacklog() {
        return backlog;
    }

    public void setBacklog(int backlog) {
        this.backlog = backlog;
    }
}
