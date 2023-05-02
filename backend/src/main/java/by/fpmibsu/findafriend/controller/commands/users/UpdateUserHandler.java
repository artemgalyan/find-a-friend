package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.UserDao;
import by.fpmibsu.findafriend.services.PasswordHasher;

public class UpdateUserHandler extends RequestHandler<Boolean, UpdateUserCommand> {
    private final PasswordHasher passwordHasher;
    private final UserDao userDao;

    public UpdateUserHandler(PasswordHasher passwordHasher, UserDao userDao) {
        this.passwordHasher = passwordHasher;
        this.userDao = userDao;
    }

    @Override
    public Boolean handle(UpdateUserCommand request) {
        var user = userDao.getEntityById(request.userId);
        if (user == null) {
            return false;
        }

        if (request.name != null) {
            user.getContacts().setName(request.name);
        }
        if (request.surname != null) {
            user.getContacts().setSurname(request.surname);
        }
        if (request.login != null) {
            user.setLogin(request.login);
        }
        if (request.password != null) {
            var hashedPassword = passwordHasher.hashPassword(request.password);
            user.setPassword(hashedPassword);
        }
        if (request.email != null) {
            user.getContacts().setEmail(request.email);
        }
        if (request.phoneNumber != null) {
            user.getContacts().setPhoneNumber(request.phoneNumber);
        }

        userDao.update(user);
        return true;
    }
}
