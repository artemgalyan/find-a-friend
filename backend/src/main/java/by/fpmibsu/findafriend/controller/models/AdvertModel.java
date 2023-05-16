package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.Advert;
import by.fpmibsu.findafriend.entity.User;

public record AdvertModel(int id, String title, String description, User owner) {
    public static AdvertModel of(Advert a) {
        if (a == null) {
            return null;
        }
        return new AdvertModel(a.getId(), a.getTitle(), a.getDescription(), a.getOwner());
    }
}