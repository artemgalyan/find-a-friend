package by.fpmibsu.findafriend.controller.commands.places;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;

public class DeletePlaceHandler extends RequestHandler<Boolean, DeletePlaceCommand> {
    private final PlaceDao placeDao;

    public DeletePlaceHandler(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    @Override
    public Boolean handle(DeletePlaceCommand request) {
        return placeDao.delete(request.getPlaceId());
    }
}