package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.controllers.UserShelterController;

public class UserShelterSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.mapController(UserShelterController.class);
    }
}
