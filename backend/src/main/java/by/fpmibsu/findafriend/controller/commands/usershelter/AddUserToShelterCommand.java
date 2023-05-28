package by.fpmibsu.findafriend.controller.commands.usershelter;

import by.fpmibsu.findafriend.application.mediatr.Request;

public class AddUserToShelterCommand extends Request<Void> {
    public int userId;
    public int shelterId;

    public AddUserToShelterCommand(int userId, int shelterId) {
        this.userId = userId;
        this.shelterId = shelterId;
    }
}
