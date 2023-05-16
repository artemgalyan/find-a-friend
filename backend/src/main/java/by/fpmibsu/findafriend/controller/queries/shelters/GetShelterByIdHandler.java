package by.fpmibsu.findafriend.controller.queries.shelters;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.ShelterModel;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;

public class GetShelterByIdHandler extends RequestHandler<ShelterModel, GetShelterByIdQuery> {
    private final ShelterDao shelterDao;

    public GetShelterByIdHandler(ShelterDao shelterDao) {
        this.shelterDao = shelterDao;
    }

    @Override
    public ShelterModel handle(GetShelterByIdQuery request) {
        return ShelterModel.of(shelterDao.getEntityById(request.getShelterId()));
    }
}