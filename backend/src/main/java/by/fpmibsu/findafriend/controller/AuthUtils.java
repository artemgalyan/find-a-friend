package by.fpmibsu.findafriend.controller;

import by.fpmibsu.findafriend.entity.User;

import java.util.Arrays;

public class AuthUtils {
    public static boolean allowRoles(String role, User.Role... roles) {
        return Arrays.stream(roles)
                .anyMatch(r -> r.toString().equals(role));
    }
}
