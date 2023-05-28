package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.mediatr.Request;

public class CreateAdvertCommand extends Request<Boolean> {
    public String advertType;
    public String title;
    public String description;
    public int placeId;
}