package by.fpmibsu.findafriend.controller.queries.shelters;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.ShelterModel;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.entity.Place;

import java.util.HashMap;

public class GetSheltersHandler extends RequestHandler<ShelterModel[], GetSheltersQuery> {
    private final ShelterDao shelterDao;
    private final PlaceDao placeDao;

    public GetSheltersHandler(ShelterDao shelterDao, PlaceDao placeDao) {
        this.shelterDao = shelterDao;
        this.placeDao = placeDao;
    }

    @Override
    public ShelterModel[] handle(GetSheltersQuery request) throws DaoException {
        var places = new HashMap<Integer, Place>();
        placeDao.getAll().forEach(p -> places.put(p.getId(), p));
        return shelterDao.getAll()
                .stream()
                .map(s -> {
                    var place = places.get(s.getPlace().getId());
                    s.setPlace(place);
                    return ShelterModel.of(s);
                })
                .toArray(ShelterModel[]::new);
    }
}
