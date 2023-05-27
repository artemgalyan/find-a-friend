package by.fpmibsu.findafriend.controller.commands.shelters;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.entity.Place;

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
        if (request.placeId != -1) {
            var p = new Place();
            p.setId(request.placeId);
            shelter.setPlace(p);
        }
        if (request.website != null) {
            shelter.setWebsite(request.website);
        }
        if (request.address != null) {
            shelter.setAddress(request.address);
        }

        shelterDao.update(shelter);
        return true;
    }
}