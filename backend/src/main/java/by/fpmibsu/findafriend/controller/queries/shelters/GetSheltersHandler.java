package by.fpmibsu.findafriend.controller.queries.shelters;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.ShelterModel;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.ShelterDao;

public class GetSheltersHandler extends RequestHandler<ShelterModel[], GetSheltersQuery> {
    private final ShelterDao shelterDao;

    public GetSheltersHandler(ShelterDao shelterDao) {
        this.shelterDao = shelterDao;
    }

    @Override
    public ShelterModel[] handle(GetSheltersQuery request) throws DaoException {
        return shelterDao.getAll()
                .stream()
                .map(ShelterModel::of)
                .toArray(ShelterModel[]::new);
    }
}
