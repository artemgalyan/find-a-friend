package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.commands.places.*;
import by.fpmibsu.findafriend.controller.controllers.PlaceController;
import by.fpmibsu.findafriend.controller.queries.places.GetPlaceByIdHandler;
import by.fpmibsu.findafriend.controller.queries.places.GetPlaceByIdQuery;
import by.fpmibsu.findafriend.controller.queries.places.GetPlacesHandler;
import by.fpmibsu.findafriend.controller.queries.places.GetPlacesQuery;

public class PlacesSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.registerHandler(GetPlacesHandler.class, GetPlacesQuery.class)
                .registerHandler(GetPlaceByIdHandler.class, GetPlaceByIdQuery.class)
                .registerHandler(CreatePlaceHandler.class, CreatePlaceCommand.class)
                .registerHandler(UpdatePlaceHandler.class, UpdatePlaceCommand.class)
                .registerHandler(DeletePlaceHandler.class, DeletePlaceCommand.class);
        builder.mapController(PlaceController.class);
    }
}
