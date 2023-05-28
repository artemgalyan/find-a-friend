package by.fpmibsu.findafriend.controller.queries.users;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;

public class GetUserIdAndLoginHandler extends RequestHandler<GetUserIdAndLoginResponse, GetUserIdAndLoginQuery> {
    private final UserDao userDao;

    public GetUserIdAndLoginHandler(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public GetUserIdAndLoginResponse handle(GetUserIdAndLoginQuery request) throws Exception {
        var user = userDao.getEntityById(request.userId);
        if (user == null) {
            return null;
        }

        return new GetUserIdAndLoginResponse(user.getId(), user.getLogin());
    }
}
