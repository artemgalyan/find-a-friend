package by.fpmibsu.find_a_friend.services;

import by.fpmibsu.find_a_friend.entity.User;

public abstract class PasswordHasher {
    public enum PasswordVerificationResult {
        FAILED,
        SUCCESS,
        SUCCESS_NEEDS_TO_BE_REHASHED
    }

    public abstract String hashPassword(String password);
    public abstract PasswordVerificationResult verifyPassword(User user, String providedPassword);
}
