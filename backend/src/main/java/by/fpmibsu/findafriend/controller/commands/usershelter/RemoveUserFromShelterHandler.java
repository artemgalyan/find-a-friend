package by.fpmibsu.findafriend.controller.commands.usershelter;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;
import by.fpmibsu.findafriend.entity.User;

public class RemoveUserFromShelterHandler extends RequestHandler<Void, RemoveUserFromShelterCommand> {
    private final UserDao userDao;
    private final UserShelterDao userShelterDao;

    public RemoveUserFromShelterHandler(UserDao userDao, UserShelterDao userShelterDao) {
        this.userDao = userDao;
        this.userShelterDao = userShelterDao;
    }

    @Override
    public Void handle(RemoveUserFromShelterCommand request) throws Exception {
        var user = userDao.getEntityById(request.userId);
        if (user == null) {
            return null;
        }

        if (User.Role.SHELTER_ADMINISTRATOR.equals(user.getRole())) {
            userDao.updateRole(user.getId(), User.Role.USER);
        }
        userShelterDao.removeUser(request.userId);
        return null;
    }
}
