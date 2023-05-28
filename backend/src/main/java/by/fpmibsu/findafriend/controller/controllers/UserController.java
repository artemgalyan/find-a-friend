package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.AuthUtils;
import by.fpmibsu.findafriend.controller.Logging;
import by.fpmibsu.findafriend.controller.Validation;
import by.fpmibsu.findafriend.controller.commands.users.CreateUserCommand;
import by.fpmibsu.findafriend.controller.commands.users.DeleteUserCommand;
import by.fpmibsu.findafriend.controller.commands.users.SetUserRoleCommand;
import by.fpmibsu.findafriend.controller.commands.users.UpdateUserCommand;
import by.fpmibsu.findafriend.controller.queries.users.GetUserByIdQuery;
import by.fpmibsu.findafriend.controller.queries.users.GetUsersQuery;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;
import by.fpmibsu.findafriend.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Arrays;

@ControllerRoute(route = "/users")
public class UserController extends Controller {
    private static final Logger logger = LogManager.getLogger(UserController.class);
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
        if (userId != request.userId && !AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR)) {
            Logging.warnNonAuthorizedAccess(this.request, logger);
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/delete", method = HttpMethod.DELETE)
    public HandleResult deleteUserById(@FromQuery(parameterName = "id") int id, @WebToken(parameterName = "id") int userId,
                                       @WebToken(parameterName = "role") String role) {
        if (userId != id && !AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR)) {
            Logging.warnNonAuthorizedAccess(this.request, logger);
            return notAuthorized();
        }
        return ok(mediatr.send(new DeleteUserCommand(id)));
    }

    @RequireAuthentication
    @Endpoint(path = "/getSelfInfo", method = HttpMethod.GET)
    public HandleResult getSelfInfo(@WebToken(parameterName = "id") int userId) {
        return getById(userId);
    }

    @RequireAuthentication
    @Endpoint(path = "/setRole", method = HttpMethod.PUT)
    public HandleResult updateRole(@FromBody SetUserRoleCommand command,
                                   @WebToken(parameterName = "role") String role) {
        if (!Validation.in(command.newRole, User.Role.USER.toString(), User.Role.MODERATOR.toString(),
                User.Role.SHELTER_ADMINISTRATOR.toString(), User.Role.ADMINISTRATOR.toString())) {
            return badRequest();
        }
        if (!AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR)
                || AuthUtils.allowRoles(command.newRole, User.Role.ADMINISTRATOR)) {
            return notAuthorized();
        }

        var user = serviceProvider.getRequiredService(UserDao.class).getEntityById(command.userId);
        if (user == null) {
            return badRequest();
        }
        if (User.Role.ADMINISTRATOR.equals(user.getRole())) {
            return notAuthorized();
        }
        return ok(mediatr.send(command));
    }

    private static boolean isAnyEmpty(String... strings) {
        return Arrays.stream(strings)
                .anyMatch(s -> s == null || "".equals(s));
    }
}
