package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.commands.animaladverts.CreateAnimalAdvertCommand;
import by.fpmibsu.findafriend.controller.commands.animaladverts.CreateAnimalAdvertsHandler;
import by.fpmibsu.findafriend.controller.controllers.AnimalAdvertsController;

public class AnimalAdvertSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.registerHandler(CreateAnimalAdvertsHandler.class, CreateAnimalAdvertCommand.class)
                .mapController(AnimalAdvertsController.class);
    }
}
