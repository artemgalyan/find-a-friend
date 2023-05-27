package by.fpmibsu.findafriend.controller.commands.auth;

import by.fpmibsu.findafriend.application.mediatr.Request;

public class SignInCommand extends Request<SignInResult> {
    public String login;
    public String password;

    public SignInCommand(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
