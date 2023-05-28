package by.fpmibsu.findafriend.controller.queries.shelters;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.ShelterModel;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;

public class GetShelterByIdHandler extends RequestHandler<ShelterModel, GetShelterByIdQuery> {
    private final ShelterDao shelterDao;
    private final PlaceDao placeDao;

    public GetShelterByIdHandler(ShelterDao shelterDao, PlaceDao placeDao) {
        this.shelterDao = shelterDao;
        this.placeDao = placeDao;
    }

    @Override
    public ShelterModel handle(GetShelterByIdQuery request) {
        var shelter = shelterDao.getEntityById(request.getShelterId());
        if (shelter == null) {
            return null;
        }
        var place = placeDao.getEntityById(shelter.getPlace().getId());
        shelter.setPlace(place);
        return ShelterModel.of(shelter);
    }
}