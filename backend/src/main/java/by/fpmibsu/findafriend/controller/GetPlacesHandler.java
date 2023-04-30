package by.fpmibsu.findafriend.controller;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.PlaceDao;
import by.fpmibsu.findafriend.entity.Place;

public class GetPlacesHandler extends RequestHandler<Place[], GetPlacesRequest> {
    private final PlaceDao placeDao;

    public GetPlacesHandler(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    @Override
    public Place[] handle(GetPlacesRequest request) {
        try {
            return placeDao.getAll().toArray(Place[]::new);
        } catch (DaoException e) {
            return null;
        }
    }
}
