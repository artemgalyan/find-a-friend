package by.fpmibsu.findafriend.application.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ControllerMapper {
    public static List<EndpointInfo> mapController(Class<? extends Controller> clazz) {
        var methods = clazz.getMethods();
        var result = new ArrayList<EndpointInfo>();
        var controllerPath = clazz.getAnnotation(ControllerRoute.class) != null
                ? clazz.getAnnotation(ControllerRoute.class).route()
                : "";
        Arrays.stream(methods)
                .filter(method -> method.getAnnotation(Endpoint.class) != null)
                .forEach(method -> {
                    var annotation = method.getAnnotation(Endpoint.class);
                    result.add(new EndpointInfo(
                            clazz, method, controllerPath + annotation.path(), annotation.method()
                    ));
                });
        return result;
    }
}
