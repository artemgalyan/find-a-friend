package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.mediatr.Request;

public class UpdateUserCommand extends Request<Boolean> {
    public int userId;
    public String name;
    public String surname;
    public String login;
    public String password;
    public String phoneNumber;
    public String email;
    public String providedPassword;

    public UpdateUserCommand() {
    }
}
