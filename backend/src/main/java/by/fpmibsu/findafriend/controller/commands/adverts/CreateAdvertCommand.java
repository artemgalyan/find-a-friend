package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.entity.Advert;
import by.fpmibsu.findafriend.entity.Place;
import by.fpmibsu.findafriend.entity.User;

import java.util.Date;

public class CreateAdvertCommand extends Request<Boolean> {
    public Advert.AdvertType advertType;
    public String title;
    public String description;
    public Date creationDate;
    public Place place;
    public User owner;
}