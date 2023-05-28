package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.mediatr.Request;

public class SetUserRoleCommand extends Request<Void> {
    public int userId;
    public String newRole;

    public SetUserRoleCommand(int userId, String newRole) {
        this.userId = userId;
        this.newRole = newRole;
    }

    public SetUserRoleCommand() {
    }
}
