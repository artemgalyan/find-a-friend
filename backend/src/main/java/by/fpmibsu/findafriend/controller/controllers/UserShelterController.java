package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.controller.AuthUtils;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;
import by.fpmibsu.findafriend.entity.User;

@ControllerRoute(route = "/userShelter")
public class UserShelterController extends Controller {

    @RequireAuthentication
    @Endpoint(path = "/addUserToShelter", method = HttpMethod.POST)
    public HandleResult addUserToShelter(@FromQuery(parameterName = "shelterId") int shelterId,
                                         @FromQuery(parameterName = "userId") int userId,
                                         @WebToken(parameterName = "role") String role) {
        if (AuthUtils.allowRoles(role, User.Role.MODERATOR, User.Role.ADMINISTRATOR)) {
            return notAuthorized();
        }

        serviceProvider.getRequiredService(UserShelterDao.class).removeUser(userId);
        serviceProvider.getRequiredService(UserShelterDao.class).add(shelterId, userId);
        return ok();
    }

    @RequireAuthentication
    @Endpoint(path = "/removeUserFromShelter", method = HttpMethod.POST)
    public HandleResult removeFromShelter(@FromQuery(parameterName = "userId") int userId,
                                          @WebToken(parameterName = "role") String role) {
        if (AuthUtils.allowRoles(role, User.Role.MODERATOR, User.Role.ADMINISTRATOR)) {
            return notAuthorized();
        }

        serviceProvider.getRequiredService(UserShelterDao.class).removeUser(userId);
        return ok();
    }
}
