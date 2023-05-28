package by.fpmibsu.findafriend.controller.commands.usershelter;

import by.fpmibsu.findafriend.application.mediatr.Request;

public class RemoveUserFromShelterCommand extends Request<Void> {
    public int userId;

    public RemoveUserFromShelterCommand(int userId) {
        this.userId = userId;
    }
}
