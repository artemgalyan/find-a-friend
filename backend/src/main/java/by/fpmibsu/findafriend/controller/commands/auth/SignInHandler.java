package by.fpmibsu.findafriend.controller.commands.auth;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.user.UserDao;
import by.fpmibsu.findafriend.services.JwtSigner;
import by.fpmibsu.findafriend.services.PasswordHasher;
import org.jose4j.jwt.JwtClaims;
import org.jose4j.lang.JoseException;

public class SignInHandler extends RequestHandler<String, SignInCommand> {
    private final JwtSigner signer;
    private final PasswordHasher passwordHasher;
    private final UserDao userDao;

    public SignInHandler(JwtSigner signer, PasswordHasher passwordHasher, UserDao userDao) {
        this.signer = signer;
        this.passwordHasher = passwordHasher;
        this.userDao = userDao;
    }

    @Override
    public String handle(SignInCommand request) throws JoseException {
        var user = userDao.findByLogin(request.login);
        if (user == null) {
            return "";
        }

        if (passwordHasher.verifyPassword(user, request.password) == PasswordHasher.PasswordVerificationResult.FAILED) {
            return "";
        }

        var claims = new JwtClaims();
        claims.setClaim("id", user.getId());
        claims.setClaim("role", user.getRole());
        return signer.signJwt(claims.toJson());
    }
}
