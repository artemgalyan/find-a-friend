package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.AdvertDao;

public class UpdateAdvertHandler extends RequestHandler<Boolean, UpdateAdvertCommand> {
    private final AdvertDao advertDao;

    public UpdateAdvertHandler(AdvertDao advertDao) {
        this.advertDao = advertDao;
    }

    @Override
    public Boolean handle(UpdateAdvertCommand request) {
        var advert = advertDao.getEntityById(request.advertId);
        if (advert == null) {
            return false;
        }
        if (request.advertType != null) {
            advert.setAdvertType(request.advertType);
        }
        if (request.title != null) {
            advert.setTitle(request.title);
        }
        if (request.description != null) {
            advert.setDescription(request.description);
        }
        if (request.creationDate != null) {
            advert.setCreationDate(request.creationDate);
        }
        if (request.place != null) {
            advert.setPlace(request.place);
        }
        if (request.owner != null) {
            advert.setOwner(request.owner);
        }

        advertDao.update(advert);
        return true;
    }
}
