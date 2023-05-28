package by.fpmibsu.findafriend.controller.commands.auth;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.validtokens.ValidTokensDao;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;
import by.fpmibsu.findafriend.entity.User;
import by.fpmibsu.findafriend.application.authentication.JwtSigner;
import by.fpmibsu.findafriend.services.PasswordHasher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

public class SignInHandler extends RequestHandler<SignInResult, SignInCommand> {
    private static final float TOKEN_EXPIRATION = 31 * 24 * 60;
    private final static Logger logger = LogManager.getLogger(SignInHandler.class);
    private final JwtSigner signer;
    private final PasswordHasher passwordHasher;
    private final UserShelterDao userShelterDao;
    private final UserDao userDao;
    private final ValidTokensDao validTokensDao;

    public SignInHandler(JwtSigner signer, PasswordHasher passwordHasher, UserShelterDao userShelterDao, UserDao userDao, ValidTokensDao validTokensDao) {
        this.signer = signer;
        this.passwordHasher = passwordHasher;
        this.userShelterDao = userShelterDao;
        this.userDao = userDao;
        this.validTokensDao = validTokensDao;
    }

    @Override
    public SignInResult handle(SignInCommand request) throws JoseException {
        var user = userDao.findByLogin(request.login);
        if (user == null) {
            logger.info(String.format("Failed to log for user %s", request.login));
            return null;
        }

        if (passwordHasher.verifyPassword(user, request.password) == PasswordHasher.PasswordVerificationResult.FAILED) {
            logger.info(String.format("Failed to log for user %s", request.login));
            return null;
        }

        var claims = new JwtClaims();
        claims.setClaim("id", user.getId());
        claims.setClaim("role", user.getRole());
        claims.setExpirationTimeMinutesInTheFuture(TOKEN_EXPIRATION);
        int shelterId = -1;
        if (User.Role.SHELTER_ADMINISTRATOR.equals(user.getRole())) {
            shelterId = userShelterDao.getShelterId(user.getId());
            claims.setClaim("shelter_id", shelterId);
        }
        logger.info(String.format("User %s logged in successfully", request.login));
        var token = signer.signJwt(claims.toJson());
        validTokensDao.addValidToken(token, user.getId());
        return new SignInResult(token, user.getId(), user.getRole().toString(), shelterId);
    }
}
