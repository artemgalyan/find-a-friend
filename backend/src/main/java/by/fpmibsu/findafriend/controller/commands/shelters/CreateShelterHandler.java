package by.fpmibsu.findafriend.controller.commands.shelters;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.ShelterDao;
import by.fpmibsu.findafriend.entity.Shelter;

import java.util.ArrayList;

public class CreateShelterHandler extends RequestHandler<Boolean, CreateShelterCommand> {
    private final ShelterDao shelterDao;

    public CreateShelterHandler(ShelterDao shelterDao) {
        this.shelterDao = shelterDao;
    }

    @Override
    public Boolean handle(CreateShelterCommand request) {
        Shelter shelter = new Shelter(0, request.name, new ArrayList<>(), new ArrayList<>(), request.place);
        try {
            shelterDao.create(shelter);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}