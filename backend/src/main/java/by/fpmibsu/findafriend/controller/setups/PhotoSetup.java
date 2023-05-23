package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.controllers.PhotoController;

public class PhotoSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.mapController(PhotoController.class);
    }
}
