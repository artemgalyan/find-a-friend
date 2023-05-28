package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;
import by.fpmibsu.findafriend.entity.User;

public class SetUserRoleHandler extends RequestHandler<Void, SetUserRoleCommand> {
    private final UserDao userDao;

    public SetUserRoleHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Void handle(SetUserRoleCommand request) throws Exception {
        userDao.updateRole(request.userId, User.Role.valueOf(request.newRole));
        return null;
    }
}
