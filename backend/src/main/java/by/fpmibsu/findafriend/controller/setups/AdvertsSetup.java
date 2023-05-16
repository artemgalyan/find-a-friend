package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.commands.adverts.*;
import by.fpmibsu.findafriend.controller.controllers.AdvertController;
import by.fpmibsu.findafriend.controller.queries.adverts.GetAdvertByIdHandler;
import by.fpmibsu.findafriend.controller.queries.adverts.GetAdvertByIdQuery;
import by.fpmibsu.findafriend.controller.queries.adverts.GetAdvertsHandler;
import by.fpmibsu.findafriend.controller.queries.adverts.GetAdvertsQuery;

public class AdvertsSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.registerHandler(CreateAdvertHandler.class, CreateAdvertCommand.class)
                .registerHandler(DeleteAdvertHandler.class, DeleteAdvertCommand.class)
                .registerHandler(UpdateAdvertHandler.class, UpdateAdvertCommand.class)
                .registerHandler(GetAdvertsHandler.class, GetAdvertsQuery.class)
                .registerHandler(GetAdvertByIdHandler.class, GetAdvertByIdQuery.class);
        builder.mapController(AdvertController.class);
    }
}
