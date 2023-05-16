package by.fpmibsu.findafriend.controller.queries.adverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.AdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.AdvertDao;

public class GetAdvertByIdHandler extends RequestHandler<AdvertModel, GetAdvertByIdQuery> {
    private final AdvertDao advertDao;

    public GetAdvertByIdHandler(AdvertDao advertDao) {
        this.advertDao = advertDao;
    }

    @Override
    public AdvertModel handle(GetAdvertByIdQuery request) {
        return AdvertModel.of(advertDao.getEntityById(request.getAdvertId()));
    }
}

