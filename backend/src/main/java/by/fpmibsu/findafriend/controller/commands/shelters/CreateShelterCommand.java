package by.fpmibsu.findafriend.controller.commands.shelters;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.entity.Place;

public class CreateShelterCommand extends Request<Boolean> {
    public String name;
    public int placeId;
}