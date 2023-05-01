package by.fpmibsu.findafriend.services;

import by.fpmibsu.findafriend.entity.User;

public class SimplePasswordHasher extends PasswordHasher {
    @Override
    public String hashPassword(String password) {
        return password;
    }

    @Override
    public PasswordVerificationResult verifyPassword(User user, String providedPassword) {
        return user.getPassword().equals(providedPassword)
                ? PasswordVerificationResult.SUCCESS
                : PasswordVerificationResult.FAILED;
    }
}
