package by.fpmibsu.findafriend.controller.commands.auth;

public record SignInResult(String token, int userId, String role, int shelterId) {}
