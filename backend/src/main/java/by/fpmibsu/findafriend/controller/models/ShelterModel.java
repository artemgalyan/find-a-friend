package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.Place;
import by.fpmibsu.findafriend.entity.Shelter;

public record ShelterModel(int id, String name, Place place, String address, String website) implements Model<Shelter> {
    public static ShelterModel of(Shelter sh) {
        if (sh == null) {
            return null;
        }
        return new ShelterModel(sh.getId(), sh.getName(), sh.getPlace(), sh.getAddress(), sh.getWebsite());
    }
}
