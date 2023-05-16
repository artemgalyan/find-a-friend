package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.commands.adverts.CreateAdvertCommand;
import by.fpmibsu.findafriend.dataaccesslayer.AdvertDao;
import by.fpmibsu.findafriend.entity.Advert;
import by.fpmibsu.findafriend.entity.Place;
import by.fpmibsu.findafriend.entity.User;

import java.util.Date;

public class CreateAdvertHandler extends RequestHandler<Boolean, CreateAdvertCommand> {
    private final AdvertDao advertDao;

    public CreateAdvertHandler(AdvertDao advertDao) {
        this.advertDao = advertDao;
    }

    @Override
    public Boolean handle(CreateAdvertCommand request) {
        var advert = new Advert(0, "dog", "very friendly", new Date(), new Place(), new User(), Advert.AdvertType.VOLUNTEER);
        try {
            advertDao.create(advert);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
