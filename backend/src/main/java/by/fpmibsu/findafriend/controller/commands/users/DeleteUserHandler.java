package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;

public class DeleteUserHandler extends RequestHandler<Boolean, DeleteUserCommand> {
    private final UserDao userDao;

    public DeleteUserHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public Boolean handle(DeleteUserCommand request) {
        return userDao.deleteUserById(request.getUserId());
    }
}
