package by.fpmibsu.findafriend.controller.commands.places;

import by.fpmibsu.findafriend.application.mediatr.Request;

public class UpdatePlaceCommand extends Request<Boolean> {
    public int placeId;
    public String country;
    public String region;
    public String city;
    public String district;

    public UpdatePlaceCommand(int placeId, String country, String region, String city, String district) {
        this.placeId = placeId;
        this.country = country;
        this.region = region;
        this.city = city;
        this.district = district;
    }
}
