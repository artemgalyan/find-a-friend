package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.UserDao;
import by.fpmibsu.findafriend.entity.Contacts;
import by.fpmibsu.findafriend.entity.User;
import by.fpmibsu.findafriend.services.PasswordHasher;

import java.util.ArrayList;

public class CreateUserHandler extends RequestHandler<Boolean, CreateUserCommand> {
    private final UserDao userDao;
    private final PasswordHasher passwordHasher;

    public CreateUserHandler(UserDao userDao, PasswordHasher passwordHasher) {
        this.userDao = userDao;
        this.passwordHasher = passwordHasher;
    }

    @Override
    public Boolean handle(CreateUserCommand request) {
        var contacts = new Contacts(request.name, request.surname, request.phoneNumber, request.email);
        var hashedPassword = passwordHasher.hashPassword(request.password);
        var user = new User(0, contacts, new ArrayList<>(), new ArrayList<>(), User.Role.USER, request.login, hashedPassword);
        try {
            userDao.create(user);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
