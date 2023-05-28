package by.fpmibsu.findafriend.controller.queries.adverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.AdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.advert.AdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;

public class GetAdvertByIdHandler extends RequestHandler<AdvertModel, GetAdvertByIdQuery> {
    private final AdvertDao advertDao;
    private final PlaceDao placeDao;

    public GetAdvertByIdHandler(AdvertDao advertDao, PlaceDao placeDao) {
        this.advertDao = advertDao;
        this.placeDao = placeDao;
    }

    @Override
    public AdvertModel handle(GetAdvertByIdQuery request) {
        var advert = advertDao.getEntityById(request.getAdvertId());
        if (advert == null) {
            return null;
        }
        var place = placeDao.getEntityById(advert.getPlace().getId());
        advert.setPlace(place);
        return AdvertModel.of(advert);
    }
}

