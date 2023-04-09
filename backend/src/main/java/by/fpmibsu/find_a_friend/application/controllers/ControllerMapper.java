package by.fpmibsu.find_a_friend.application.controllers;

import by.fpmibsu.find_a_friend.application.endpoints.EndpointClassVerifier;
import by.fpmibsu.find_a_friend.application.endpoints.EndpointInfo;
import by.fpmibsu.find_a_friend.services.Request;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerMapper {
    public static List<EndpointInfo> mapController(Class<? extends Controller> clazz) {
        var route = getControllerRoute(clazz);
        var result = new ArrayList<EndpointInfo>();
        var methods = clazz.getMethods();
        Arrays.stream(methods)
                .filter(m -> m.getAnnotation(Endpoint.class) != null)
                .forEach(m -> result.add(mapMethod(m, route)));
        return result;
    }

    private static String getControllerRoute(Class<? extends Controller> clazz) {
        var annotation = clazz.getAnnotation(ControllerRoute.class);
        if (annotation == null) {
            return "/";
        }
        var route = annotation.route();
        if (!route.startsWith("/")) {
            route = "/" + route;
        }
        if (!route.endsWith("/")) {
            route = route + "/";
        }
        return route;
    }

    private static EndpointInfo<?, ?> mapMethod(Method method, String prefix) {
        var annotation = method.getAnnotation(Endpoint.class);
        var returnType = method.getReturnType();
        var params = method.getParameterTypes();
        if (params.length != 1 || !Request.class.isAssignableFrom(params[0]) || params[0].isInterface()) {
            throw new EndpointParameterException("There are more than 1 parameter in endpoint " + method.getName() + " or it is not a request or it is an interface");
        }
        var requestType = params[0];
        var handlerType = annotation.requestHandler();
        var route = annotation.route();
        var httpMethod = annotation.method();
        if (route == null || httpMethod == null) {
            throw new EndpointParameterException("Route or http method of " + method.getName() + " are not specified");
        }
        if (!EndpointClassVerifier.verifyRequestAndResponseTypes(requestType, returnType, handlerType)) {
            throw new EndpointParameterException("Response and request do not match for " + method.getName());
        }
        if (route.startsWith("/")) {
            route = route.substring(1);
        }

        return new EndpointInfo(prefix + route, requestType, returnType, handlerType, httpMethod);
    }
}
