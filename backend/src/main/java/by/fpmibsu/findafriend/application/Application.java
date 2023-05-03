package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.controller.EndpointInfo;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.findafriend.controller.setups.UsersSetup;
import by.fpmibsu.findafriend.dataaccesslayer.DaoSetup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

public class Application {
    private static final List<Setup> setups = List.of(new DaoSetup(), new UsersSetup());
    private final GlobalServiceProvider globalServiceProvider;
    private final List<EndpointInfo> endpointInfos;

    public Application(GlobalServiceProvider globalServiceProvider, List<EndpointInfo> endpointInfos) {
        this.globalServiceProvider = globalServiceProvider;
        this.endpointInfos = endpointInfos;
    }

    public void send(HttpServletRequest request, HttpServletResponse response) {
        // TODO
        EndpointInfo endpoint = null; // найти эндпоинт по запросу
        HandleResult result = ControllerMethodInvoker.invoke(request, response, endpoint, globalServiceProvider.getRequestServiceProvider());
        /// записать в response
    }
}
