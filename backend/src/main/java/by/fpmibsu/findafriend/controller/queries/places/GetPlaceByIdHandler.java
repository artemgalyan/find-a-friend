package by.fpmibsu.findafriend.controller.queries.places;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.PlaceModel;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;

public class GetPlaceByIdHandler extends RequestHandler<PlaceModel, GetPlaceByIdQuery> {
    private final PlaceDao placeDao;

    public GetPlaceByIdHandler(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    @Override
    public PlaceModel handle(GetPlaceByIdQuery request) {
        return PlaceModel.of(placeDao.getEntityById(request.getPlaceId()));
    }
}




