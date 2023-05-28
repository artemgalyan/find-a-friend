package by.fpmibsu.findafriend.controller.setups;

import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.controllers.PhotoController;
import by.fpmibsu.findafriend.controller.queries.photos.GetAdvertPreviewHandler;
import by.fpmibsu.findafriend.controller.queries.photos.GetAdvertPreviewRequest;
import by.fpmibsu.findafriend.controller.queries.photos.GetPhotosByAdvertIdHandler;
import by.fpmibsu.findafriend.controller.queries.photos.GetPhotosByAdvertIdQuery;

public class PhotoSetup extends Setup {
    @Override
    public void applyTo(ApplicationBuilder builder) {
        builder.mapController(PhotoController.class)
                .registerHandler(GetPhotosByAdvertIdHandler.class, GetPhotosByAdvertIdQuery.class)
                .registerHandler(GetAdvertPreviewHandler.class, GetAdvertPreviewRequest.class);
    }
}
