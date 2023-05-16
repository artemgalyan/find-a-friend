package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.advert.AdvertDao;
import by.fpmibsu.findafriend.entity.Advert;

public class CreateAdvertHandler extends RequestHandler<Boolean, CreateAdvertCommand> {
    private final AdvertDao advertDao;

    public CreateAdvertHandler(AdvertDao advertDao) {
        this.advertDao = advertDao;
    }

    @Override
    public Boolean handle(CreateAdvertCommand request) {
        var advert = new Advert(0, request.title, request.description, request.creationDate, request.place, request.owner, request.advertType);
        try {
            advertDao.create(advert);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
