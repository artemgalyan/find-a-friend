package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.controller.EndpointInfo;
import by.fpmibsu.findafriend.application.serviceproviders.ScopedServiceProvider;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerMethodInvoker {
    public static HandleResult invoke(HttpServletRequest request, HttpServletResponse response,
                                      EndpointInfo endpointInfo, ScopedServiceProvider scopedServiceProvider) {
        /// распарсить параметры метода и вызвать его, вернуть то, что вернул тот метод
        /// Если какая-то ошибка, то вернуть соответствующий http-код, например, new HandleResult(404);
        return null;
    }
}
