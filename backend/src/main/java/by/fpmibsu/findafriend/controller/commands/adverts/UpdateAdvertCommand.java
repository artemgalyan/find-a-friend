package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.entity.Advert;
import by.fpmibsu.findafriend.entity.Place;
import by.fpmibsu.findafriend.entity.User;

import java.util.Date;

public class UpdateAdvertCommand extends Request<Boolean> {
    public int advertId;
    public Advert.AdvertType advertType;
    public String title;
    public String description;
    public Date creationDate;
    public Place place;
    public User owner;

    public UpdateAdvertCommand(int advertId, Advert.AdvertType advertType, String title, String description, Date creationDate, Place place, User owner) {
        this.advertId = advertId;
        this.advertType = advertType;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.place = place;
        this.owner = owner;
    }
}