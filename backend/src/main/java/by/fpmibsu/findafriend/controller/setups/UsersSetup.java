package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.commands.users.*;
import by.fpmibsu.findafriend.controller.controllers.UserController;
import by.fpmibsu.findafriend.controller.queries.users.GetUserByIdHandler;
import by.fpmibsu.findafriend.controller.queries.users.GetUserByIdQuery;
import by.fpmibsu.findafriend.controller.queries.users.GetUsersHandler;
import by.fpmibsu.findafriend.controller.queries.users.GetUsersQuery;

public class UsersSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.registerHandler(CreateUserHandler.class, CreateUserCommand.class)
                .registerHandler(DeleteUserHandler.class, DeleteUserCommand.class)
                .registerHandler(UpdateUserHandler.class, UpdateUserCommand.class)
                .registerHandler(GetUsersHandler.class, GetUsersQuery.class)
                .registerHandler(GetUserByIdHandler.class, GetUserByIdQuery.class)
                .registerHandler(SetUserRoleHandler.class, SetUserRoleCommand.class);
        builder.mapController(UserController.class);
    }
}
