package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.mediatr.Request;

public class CreateUserCommand extends Request<Boolean> {
    public String name;
    public String surname;
    public String login;
    public String password;
    public String email;
    public String phoneNumber;
}
