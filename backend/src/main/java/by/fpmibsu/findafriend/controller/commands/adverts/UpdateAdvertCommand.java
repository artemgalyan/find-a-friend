package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.entity.Advert;
import by.fpmibsu.findafriend.entity.Place;

import java.util.Date;

public class UpdateAdvertCommand extends Request<Boolean> {
    public int advertId;
    public Integer advertType;
    public String title;
    public String description;
    public Date creationDate;
    public Place place;

    public UpdateAdvertCommand(int advertId, Integer advertType, String title, String description, Date creationDate, Place place) {
        this.advertId = advertId;
        this.advertType = advertType;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.place = place;
    }
}