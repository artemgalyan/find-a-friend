package by.fpmibsu.find_a_friend.services;

import by.fpmibsu.find_a_friend.entity.User;

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
