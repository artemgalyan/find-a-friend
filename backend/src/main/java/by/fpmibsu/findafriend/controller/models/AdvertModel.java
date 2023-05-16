package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.Advert;

public record AdvertModel(int id) {
    public static AdvertModel of(Advert a) {
        if (a == null) {
            return null;
        }
        return new AdvertModel(a.getId());
    }
}