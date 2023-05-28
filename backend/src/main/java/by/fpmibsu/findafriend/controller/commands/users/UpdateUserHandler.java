package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;
import by.fpmibsu.findafriend.dataaccesslayer.validtokens.ValidTokensDao;
import by.fpmibsu.findafriend.services.PasswordHasher;

public class UpdateUserHandler extends RequestHandler<Boolean, UpdateUserCommand> {
    private final PasswordHasher passwordHasher;
    private final UserDao userDao;
    private final ValidTokensDao tokensDao;
    public UpdateUserHandler(PasswordHasher passwordHasher, UserDao userDao, ValidTokensDao tokensDao) {
        this.passwordHasher = passwordHasher;
        this.userDao = userDao;
        this.tokensDao = tokensDao;
    }

    @Override
    public Boolean handle(UpdateUserCommand request) {
        var user = userDao.getEntityById(request.userId);
        if (user == null) {
            return false;
        }

        boolean invalidated = false;
        if (request.name != null) {
            user.getContacts().setName(request.name);
        }
        if (request.surname != null) {
            user.getContacts().setSurname(request.surname);
        }
        if (request.email != null) {
            user.getContacts().setEmail(request.email);
        }
        if (request.phoneNumber != null) {
            user.getContacts().setPhoneNumber(request.phoneNumber);
        }

        if (request.login != null || request.password != null) {
            boolean passwordIsValid = passwordHasher.verifyPassword(user, request.providedPassword) != PasswordHasher.PasswordVerificationResult.FAILED;
            if (passwordIsValid && request.login != null) {
                invalidated |= !request.login.equals(user.getLogin());
                user.setLogin(request.login);
            }
            if (passwordIsValid && request.password != null) {
                var hashedPassword = passwordHasher.hashPassword(request.password);
                invalidated |= !user.getPassword().equals(hashedPassword);
                user.setPassword(hashedPassword);
            }
        }

        userDao.update(user);
        if (invalidated) {
            tokensDao.invalidateTokens(user.getId());
        }
        return invalidated;
    }
}
