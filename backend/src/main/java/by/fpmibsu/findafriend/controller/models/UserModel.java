package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.User;

public record UserModel(int id, String name, String surname, String phoneNumber, String email) implements Model<User> {
    public static UserModel of(User u) {
        if (u == null) {
            return null;
        }
        return new UserModel(u.getId(),
                u.getContacts().getName(), u.getContacts().getSurname(),
                u.getContacts().getPhoneNumber(), u.getContacts().getEmail()
        );
    }
}
