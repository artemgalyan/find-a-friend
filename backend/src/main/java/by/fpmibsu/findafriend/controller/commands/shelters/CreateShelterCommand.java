package by.fpmibsu.findafriend.controller.commands.shelters;

import by.fpmibsu.findafriend.application.mediatr.Request;

public class CreateShelterCommand extends Request<Boolean> {
    public String name;
    public String address;
    public String website;
    public int placeId;
}