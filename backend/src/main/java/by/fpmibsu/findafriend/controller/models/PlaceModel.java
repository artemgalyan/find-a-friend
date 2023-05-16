package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.Place;

public record PlaceModel(int id, String country, String region, String city, String district) {
    public static PlaceModel of(Place place) {
        if (place == null) {
            return null;
        }
        return new PlaceModel(place.getId(), place.getCountry(), place.getRegion(), place.getCity(), place.getDistrict());
    }
}