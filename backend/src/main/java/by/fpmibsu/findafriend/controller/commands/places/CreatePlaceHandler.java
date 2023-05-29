package by.fpmibsu.findafriend.controller.commands.places;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.commands.adverts.CreateAdvertHandler;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;
import by.fpmibsu.findafriend.entity.Place;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreatePlaceHandler extends RequestHandler<Boolean, CreatePlaceCommand> {
    private final Logger logger = LogManager.getLogger(CreatePlaceHandler.class);
    private final PlaceDao placeDao;

    public CreatePlaceHandler(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    @Override
    public Boolean handle(CreatePlaceCommand request) {
        Place place = new Place(0, request.country, request.region, request.city, request.district);
        placeDao.create(place);
        return true;
    }
}

