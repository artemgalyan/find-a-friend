package by.fpmibsu.findafriend.controller.queries.users;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.UserModel;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;

public class GetUserByIdHandler extends RequestHandler<UserModel, GetUserByIdQuery> {
    private final UserDao userDao;

    public GetUserByIdHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserModel handle(GetUserByIdQuery request) {
        return UserModel.of(userDao.getEntityById(request.getUserId()));
    }
}
