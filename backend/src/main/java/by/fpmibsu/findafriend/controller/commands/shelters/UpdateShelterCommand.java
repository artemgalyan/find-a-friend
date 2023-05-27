package by.fpmibsu.findafriend.controller.commands.shelters;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.entity.Place;

public class UpdateShelterCommand extends Request<Boolean> {
    public int shelterId;
    public String name;
    public int placeId;

    public UpdateShelterCommand(int shelterId, String name, int placeId) {
        this.shelterId = shelterId;
        this.name = name;
        this.placeId = placeId;
    }
}