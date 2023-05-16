package by.fpmibsu.findafriend.controller.commands.places;

import by.fpmibsu.findafriend.application.mediatr.Request;
public class CreatePlaceCommand extends Request<Boolean> {
    public String country;
    public String region;
    public String city;
    public String district;
}
