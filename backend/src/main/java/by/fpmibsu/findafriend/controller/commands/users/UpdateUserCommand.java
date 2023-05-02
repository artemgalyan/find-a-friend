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

    public UpdateUserCommand(int userId, String name, String surname, String login, String password, String phoneNumber, String email) {
        this.userId = userId;
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.email = email;
    }
}
