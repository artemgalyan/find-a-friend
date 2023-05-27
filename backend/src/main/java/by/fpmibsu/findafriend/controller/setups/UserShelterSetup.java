package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.commands.usershelter.AddUserToShelterCommand;
import by.fpmibsu.findafriend.controller.commands.usershelter.AddUserToShelterHandler;
import by.fpmibsu.findafriend.controller.commands.usershelter.RemoveUserFromShelterCommand;
import by.fpmibsu.findafriend.controller.commands.usershelter.RemoveUserFromShelterHandler;
import by.fpmibsu.findafriend.controller.controllers.UserShelterController;

public class UserShelterSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.mapController(UserShelterController.class)
                .registerHandler(AddUserToShelterHandler.class, AddUserToShelterCommand.class)
                .registerHandler(RemoveUserFromShelterHandler.class, RemoveUserFromShelterCommand.class);
    }
}
