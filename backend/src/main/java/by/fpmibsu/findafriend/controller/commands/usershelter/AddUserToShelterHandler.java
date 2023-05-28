package by.fpmibsu.findafriend.controller.commands.usershelter;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;
import by.fpmibsu.findafriend.entity.User;

public class AddUserToShelterHandler extends RequestHandler<Void, AddUserToShelterCommand> {
    private final UserDao userDao;
    private final UserShelterDao userShelterDao;

    public AddUserToShelterHandler(UserDao userDao, UserShelterDao userShelterDao) {
        this.userDao = userDao;
        this.userShelterDao = userShelterDao;
    }

    @Override
    public Void handle(AddUserToShelterCommand request) throws Exception {
        var user = userDao.getEntityById(request.userId);
        if (user == null) {
            return null;
        }
        if (User.Role.USER.equals(user.getRole())) {
            userDao.updateRole(user.getId(), User.Role.SHELTER_ADMINISTRATOR);
        }
        userShelterDao.removeUser(user.getId());
        userShelterDao.add(request.shelterId, request.userId);
        return null;
    }
}
