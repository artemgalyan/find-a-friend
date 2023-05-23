package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;
import by.fpmibsu.findafriend.entity.User;

@ControllerRoute(route = "/userShelter")
public class UserShelterController extends Controller {
    private final UserShelterDao userShelterDao;

    public UserShelterController(UserShelterDao userShelterDao) {
        this.userShelterDao = userShelterDao;
    }

    @RequireAuthentication
    @Endpoint(path = "/addUserToShelter", method = HttpMethod.POST)
    public HandleResult addUserToShelter(@FromQuery(parameterName = "shelterId") int shelterId,
                                         @FromQuery(parameterName = "userId") int userId,
                                         @WebToken(parameterName = "role") String role) {
        if (!User.Role.SHELTER_ADMINISTRATOR.toString().equals(role)) {
            return notAuthorized();
        }

        userShelterDao.add(shelterId, userId);
        return ok();
    }

    @RequireAuthentication
    @Endpoint(path = "/removeUserFromShelter", method = HttpMethod.POST)
    public HandleResult removeFromShelter(@FromQuery(parameterName = "shelterId") int shelterId,
                                          @FromQuery(parameterName = "userId") int userId,
                                          @WebToken(parameterName = "role") String role) {
        if (!User.Role.SHELTER_ADMINISTRATOR.toString().equals(role)) {
            return notAuthorized();
        }

        userShelterDao.remove(shelterId, userId);
        return ok();
    }
}
