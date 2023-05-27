package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.commands.users.CreateUserCommand;
import by.fpmibsu.findafriend.controller.commands.users.DeleteUserCommand;
import by.fpmibsu.findafriend.controller.commands.users.UpdateUserCommand;
import by.fpmibsu.findafriend.controller.queries.users.GetUserByIdQuery;
import by.fpmibsu.findafriend.controller.queries.users.GetUsersQuery;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;
import by.fpmibsu.findafriend.entity.User;

import java.util.Arrays;

@ControllerRoute(route = "/users")
public class UserController extends Controller {
    private final Mediatr mediatr;

    public UserController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getAll", method = HttpMethod.GET)
    public HandleResult getAll() {
        return ok(mediatr.send(new GetUsersQuery()));
    }

    @RequireAuthentication
    @Endpoint(path = "/getById", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) {
        return ok(mediatr.send(new GetUserByIdQuery(id)));
    }

    @Endpoint(path = "/create", method = HttpMethod.POST)
    public HandleResult createUser(@FromBody CreateUserCommand request) {
        if (isAnyEmpty(request.name, request.surname, request.login, request.password, request.email, request.phoneNumber)) {
            return badRequest();
        }
        if (request.password.length() < 8) {
            return badRequest("Too short password");
        }
        var userDao = serviceProvider.getRequiredService(UserDao.class);
        if (userDao.findByLogin(request.login) != null) {
            return badRequest("Duplicate login");
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/update", method = HttpMethod.PUT)
    public HandleResult updateUser(@FromBody UpdateUserCommand request, @WebToken(parameterName = "id") int userId,
                                   @WebToken(parameterName = "role") String role) {
        if (userId != request.userId && !User.Role.ADMINISTRATOR.toString().equals(role)) {
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/delete", method = HttpMethod.DELETE)
    public HandleResult deleteUserById(@WebToken(parameterName = "id") int id, @WebToken(parameterName = "id") int userId,
                                       @WebToken(parameterName = "role") String role) {
        if (userId != id && !User.Role.ADMINISTRATOR.toString().equals(role)) {
            return notAuthorized();
        }
        return ok(mediatr.send(new DeleteUserCommand(id)));
    }

    @RequireAuthentication
    @Endpoint(path = "/getSelfInfo", method = HttpMethod.GET)
    public HandleResult getSelfInfo(@WebToken(parameterName = "id") int userId) {
        return getById(userId);
    }

    private static boolean isAnyEmpty(String... strings) {
        return Arrays.stream(strings)
                .anyMatch(s -> s == null || "".equals(s));
    }
}
