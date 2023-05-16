package by.fpmibsu.findafriend.controller.commands.places;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.PlaceDao;
import by.fpmibsu.findafriend.entity.Place;

public class CreatePlaceHandler extends RequestHandler<Boolean, CreatePlaceCommand> {
    private final PlaceDao placeDao;

    public CreatePlaceHandler(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    @Override
    public Boolean handle(CreatePlaceCommand request) {
        Place place = new Place(0, request.country, request.region, request.city, request.district);
        try {
            placeDao.create(place);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}

