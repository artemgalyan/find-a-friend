package by.fpmibsu.findafriend.controller.queries.users;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.UserModel;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.UserDao;

public class GetUsersHandler extends RequestHandler<UserModel[], GetUsersQuery> {
    private final UserDao userDao;

    public GetUsersHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public UserModel[] handle(GetUsersQuery request) throws DaoException {
        return userDao.getAll().stream().map(UserModel::of).toArray(UserModel[]::new);
    }
}
