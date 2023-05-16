package by.fpmibsu.findafriend.controller.queries.places;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.PlaceModel;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.PlaceDao;

public class GetPlacesHandler extends RequestHandler<PlaceModel[], GetPlacesQuery> {
    private final PlaceDao placeDao;

    public GetPlacesHandler(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    @Override
    public PlaceModel[] handle(GetPlacesQuery request) throws DaoException {
        return placeDao.getAll()
                .stream()
                .map(PlaceModel::of)
                .toArray(PlaceModel[]::new);
    }
}
