package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.commands.auth.SignInCommand;

@ControllerRoute(route = "/auth")
public class AuthController extends Controller {
    private final Mediatr mediatr;

    public AuthController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/signIn", method = HttpMethod.GET)
    public HandleResult signIn(@FromQuery(parameterName = "login") String login,
                               @FromQuery(parameterName = "password") String password) {
        return ok(mediatr.send(new SignInCommand(login, password)));
    }
}
