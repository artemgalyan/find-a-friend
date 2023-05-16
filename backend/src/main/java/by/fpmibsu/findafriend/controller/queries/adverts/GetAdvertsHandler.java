package by.fpmibsu.findafriend.controller.queries.adverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.AdvertModel;
import by.fpmibsu.findafriend.controller.queries.adverts.GetAdvertsQuery;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.AdvertDao;

public class GetAdvertsHandler extends RequestHandler<AdvertModel[], GetAdvertsQuery> {
    private final AdvertDao advertDaoDao;

    public GetAdvertsHandler(AdvertDao advertDao) {
        this.advertDaoDao = advertDao;
    }

    @Override
    public AdvertModel[] handle(GetAdvertsQuery request) throws DaoException {
        return advertDaoDao.getAll().stream().map(AdvertModel::of).toArray(AdvertModel[]::new);
    }
}
