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

    @Endpoint(path = "/getAll", method = HttpMethod.GET)
    public HandleResult getAll() {
        return Ok(mediatr.send(new GetUsersQuery()));
    }

    @Endpoint(path = "/getUser", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) {
//        return new HandleResult(200, mediatr.send(new GetUserByIdQuery(id)));
        return Ok(mediatr.send(new GetUserByIdQuery(id)));
    }

    @Endpoint(path = "/createUser", method = HttpMethod.POST)
    public HandleResult createPlace(@FromBody CreateUserCommand request) {
        return Ok(mediatr.send(request));
    }

    @Endpoint(path = "/updateUser", method = HttpMethod.PUT)
    public HandleResult updateUser(@FromBody UpdateUserCommand request) {
        return Ok(mediatr.send(request));
    }

    @Endpoint(path = "/deleteUser", method = HttpMethod.DELETE)
    public HandleResult deleteUserById(@FromQuery(parameterName = "id") int id) {
        return Ok(mediatr.send(new DeleteUserCommand(id)));
    }
}
