package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.commands.users.CreateUserCommand;
import by.fpmibsu.findafriend.controller.commands.users.DeleteUserCommand;
import by.fpmibsu.findafriend.controller.commands.users.UpdateUserCommand;
import by.fpmibsu.findafriend.controller.queries.users.GetUserByIdQuery;
import by.fpmibsu.findafriend.controller.queries.users.GetUsersQuery;

@ControllerRoute(route = "/users")
public class UserController extends Controller {
    private final Mediatr mediatr;

    public UserController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @RequireAuthentication
    @Endpoint(path = "/getAll", method = HttpMethod.GET)
    public HandleResult getAll() {
        return ok(mediatr.send(new GetUsersQuery()));
    }

    @RequireAuthentication
    @Endpoint(path = "/getUser", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) {
        return ok(mediatr.send(new GetUserByIdQuery(id)));
    }

    @Endpoint(path = "/createUser", method = HttpMethod.POST)
    public HandleResult createUser(@FromBody CreateUserCommand request) {
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/updateUser", method = HttpMethod.PUT)
    public HandleResult updateUser(@FromBody UpdateUserCommand request, @WebToken(parameterName = "id") int userId) {
        if (userId != request.userId) {
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/deleteUser", method = HttpMethod.DELETE)
    public HandleResult deleteUserById(@WebToken(parameterName = "id") int id) {
        return ok(mediatr.send(new DeleteUserCommand(id)));
    }
}
