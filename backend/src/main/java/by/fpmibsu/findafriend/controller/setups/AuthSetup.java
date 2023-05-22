package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.commands.auth.SignInCommand;
import by.fpmibsu.findafriend.controller.commands.auth.SignInHandler;
import by.fpmibsu.findafriend.controller.controllers.AuthController;

public class AuthSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.registerHandler(SignInHandler.class, SignInCommand.class)
                .mapController(AuthController.class);
    }
}
