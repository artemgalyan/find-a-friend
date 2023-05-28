package by.fpmibsu.findafriend.controller.queries.adverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.AdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.advert.AdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;
import by.fpmibsu.findafriend.entity.Place;

import java.util.HashMap;

public class GetAdvertsHandler extends RequestHandler<AdvertModel[], GetAdvertsQuery> {
    private final AdvertDao advertDaoDao;
    private final PlaceDao placeDao;

    public GetAdvertsHandler(AdvertDao advertDao, PlaceDao placeDao) {
        this.advertDaoDao = advertDao;
        this.placeDao = placeDao;
    }

    @Override
    public AdvertModel[] handle(GetAdvertsQuery request) throws DaoException {
        var places = new HashMap<Integer, Place>();
        placeDao.getAll().forEach(p -> places.put(p.getId(), p));
        return advertDaoDao.getAll()
                .stream()
                .map(a -> {
                    var place = places.get(a.getPlace().getId());
                    a.setPlace(place);
                    return AdvertModel.of(a);
                })
                .toArray(AdvertModel[]::new);
    }
}
