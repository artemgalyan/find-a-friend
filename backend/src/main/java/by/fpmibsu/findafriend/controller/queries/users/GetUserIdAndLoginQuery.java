package by.fpmibsu.findafriend.controller.queries.users;

import by.fpmibsu.findafriend.application.mediatr.Request;

public class GetUserIdAndLoginQuery extends Request<GetUserIdAndLoginResponse> {
    public int userId;

    public GetUserIdAndLoginQuery(int userId) {
        this.userId = userId;
    }
}
