package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.AuthUtils;
import by.fpmibsu.findafriend.controller.Logging;
import by.fpmibsu.findafriend.controller.commands.usershelter.AddUserToShelterCommand;
import by.fpmibsu.findafriend.controller.commands.usershelter.RemoveUserFromShelterCommand;
import by.fpmibsu.findafriend.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ControllerRoute(route = "/userShelter")
public class UserShelterController extends Controller {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    private final Mediatr mediatr;

    public UserShelterController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @RequireAuthentication
    @Endpoint(path = "/addUserToShelter", method = HttpMethod.POST)
    public HandleResult addUserToShelter(@FromQuery(parameterName = "shelterId") int shelterId,
                                         @FromQuery(parameterName = "userId") int userId,
                                         @WebToken(parameterName = "role") String role) throws Exception {
        if (!AuthUtils.allowRoles(role, User.Role.MODERATOR, User.Role.ADMINISTRATOR)) {
            Logging.warnNonAuthorizedAccess(this.request, logger);
            return notAuthorized();
        }

        return ok(mediatr.send(new AddUserToShelterCommand(userId, shelterId)));
    }

    @RequireAuthentication
    @Endpoint(path = "/removeUserFromShelter", method = HttpMethod.POST)
    public HandleResult removeFromShelter(@FromQuery(parameterName = "userId") int userId,
                                          @WebToken(parameterName = "role") String role) throws Exception {
        if (!AuthUtils.allowRoles(role, User.Role.MODERATOR, User.Role.ADMINISTRATOR)) {
            Logging.warnNonAuthorizedAccess(this.request, logger);
            return notAuthorized();
        }

        return ok(mediatr.send(new RemoveUserFromShelterCommand(userId)));
    }
}
