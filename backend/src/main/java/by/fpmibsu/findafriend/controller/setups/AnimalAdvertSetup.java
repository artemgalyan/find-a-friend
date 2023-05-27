package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.commands.animaladverts.CreateAnimalAdvertCommand;
import by.fpmibsu.findafriend.controller.commands.animaladverts.CreateAnimalAdvertsHandler;
import by.fpmibsu.findafriend.controller.controllers.AnimalAdvertsController;
import by.fpmibsu.findafriend.controller.queries.animalAdverts.*;

public class AnimalAdvertSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.registerHandler(CreateAnimalAdvertsHandler.class, CreateAnimalAdvertCommand.class)
                .registerHandler(GetAllAnimalAdvertsHandler.class, GetAllAnimalAdvertsQuery.class)
                .registerHandler(GetAnimalAdvertHandler.class, GetAnimalAdvertQuery.class)
                .registerHandler(GetAnimalAdvertsByUserIdHandler.class, GetAnimalAdvertsByUserIdQuery.class)
                .registerHandler(GetAnimalAdvertsByShelterIdHandler.class, GetAnimalAdvertsByShelterIdQuery.class)
                .mapController(AnimalAdvertsController.class);
    }
}
