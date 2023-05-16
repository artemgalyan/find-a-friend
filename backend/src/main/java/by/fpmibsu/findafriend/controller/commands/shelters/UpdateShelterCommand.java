package by.fpmibsu.findafriend.controller.commands.shelters;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.entity.Place;

public class UpdateShelterCommand extends Request<Boolean> {
    public int shelterId;
    public String name;
    public Place place;

    public UpdateShelterCommand(int shelterId, String name, Place place) {
        this.shelterId = shelterId;
        this.name = name;
        this.place = place;
    }
}