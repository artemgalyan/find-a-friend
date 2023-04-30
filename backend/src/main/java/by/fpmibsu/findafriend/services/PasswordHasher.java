package by.fpmibsu.findafriend.services;

import by.fpmibsu.findafriend.entity.User;

public abstract class PasswordHasher {
    public enum PasswordVerificationResult {
        FAILED,
        SUCCESS,
        SUCCESS_NEEDS_TO_BE_REHASHED
    }

    public abstract String hashPassword(String password);
    public abstract PasswordVerificationResult verifyPassword(User user, String providedPassword);
}
