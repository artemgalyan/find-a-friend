package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.commands.shelters.*;
import by.fpmibsu.findafriend.controller.controllers.ShelterController;
import by.fpmibsu.findafriend.controller.queries.shelters.GetShelterByIdHandler;
import by.fpmibsu.findafriend.controller.queries.shelters.GetShelterByIdQuery;
import by.fpmibsu.findafriend.controller.queries.shelters.GetSheltersHandler;
import by.fpmibsu.findafriend.controller.queries.shelters.GetSheltersQuery;

public class SheltersSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.registerHandler(CreateShelterHandler.class, CreateShelterCommand.class)
                .registerHandler(DeleteShelterHandler.class, DeleteShelterCommand.class)
                .registerHandler(UpdateShelterHandler.class, UpdateShelterCommand.class)
                .registerHandler(GetSheltersHandler.class, GetSheltersQuery.class)
                .registerHandler(GetShelterByIdHandler.class, GetShelterByIdQuery.class);
        builder.mapController(ShelterController.class);
    }
}