package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;

@ControllerRoute(route = "/example")
public class ExampleController extends Controller {
    @RequireAuthentication
    @Endpoint(path = "/protected", method = HttpMethod.GET)
    public HandleResult example(@WebToken(parameterName = "id") int id,
                                @WebToken(parameterName = "role") String role) {
        return ok(role);
    }
}
