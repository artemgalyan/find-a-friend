package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.Advert;
import by.fpmibsu.findafriend.entity.Place;
import by.fpmibsu.findafriend.entity.User;

import java.util.Date;

public record AdvertModel(int id, String title, String description, int ownerId, String advertType, Place place,
                          Date creationDate) {
    public static AdvertModel of(Advert a) {
        if (a == null) {
            return null;
        }
        return new AdvertModel(a.getId(), a.getTitle(), a.getDescription(), a.getOwner().getId(), a.getAdvertType().toString(), a.getPlace(), a.getCreationDate());
    }
}