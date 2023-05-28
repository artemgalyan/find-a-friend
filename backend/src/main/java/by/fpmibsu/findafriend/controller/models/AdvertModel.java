package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.Advert;
import by.fpmibsu.findafriend.entity.Place;

import java.util.Date;

public class AdvertModel implements Model<Advert> {
    public int id;
    public String title;
    public String description;
    public int ownerId;
    public String advertType;
    public Place place;
    public Date creationDate;

    public AdvertModel(int id, String title, String description, int ownerId, String advertType, Place place, Date creationDate) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.ownerId = ownerId;
        this.advertType = advertType;
        this.place = place;
        this.creationDate = creationDate;
    }

    public static AdvertModel of(Advert a) {
        if (a == null) {
            return null;
        }
        return new AdvertModel(a.getId(), a.getTitle(), a.getDescription(), a.getOwner().getId(), a.getAdvertType().toString(), a.getPlace(), a.getCreationDate());
    }
}