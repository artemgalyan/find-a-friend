package by.fpmibsu.findafriend.controller.queries.users;

public class GetUserIdAndLoginResponse {
    public int userId;
    public String login;

    public GetUserIdAndLoginResponse(int userId, String login) {
        this.userId = userId;
        this.login = login;
    }
}
