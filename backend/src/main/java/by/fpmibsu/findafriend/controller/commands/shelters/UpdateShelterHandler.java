package by.fpmibsu.findafriend.controller.commands.shelters;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;

public class UpdateShelterHandler extends RequestHandler<Boolean, UpdateShelterCommand> {
    private final ShelterDao shelterDao;

    public UpdateShelterHandler(ShelterDao shelterDao) {
        this.shelterDao = shelterDao;
    }

    @Override
    public Boolean handle(UpdateShelterCommand request) {
        var shelter = shelterDao.getEntityById(request.shelterId);
        if (shelter == null) {
            return false;
        }
        if (request.name != null) {
            shelter.setName(request.name);
        }
        if (request.place != null) {
            shelter.setPlace(request.place);
        }

        shelterDao.update(shelter);
        return true;
    }
}