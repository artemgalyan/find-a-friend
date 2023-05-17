package by.fpmibsu.findafriend.controller.commands.shelters;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;

public class DeleteShelterHandler extends RequestHandler<Boolean, DeleteShelterCommand> {
    private final ShelterDao shelterDao;

    public DeleteShelterHandler(ShelterDao userDao) {
        this.shelterDao = userDao;
    }

    @Override
    public Boolean handle(DeleteShelterCommand request) {
        return shelterDao.deleteShelterById(request.getShelterId());
    }
}