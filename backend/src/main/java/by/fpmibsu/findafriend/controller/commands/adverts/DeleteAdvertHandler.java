package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.advert.AdvertDao;

public class DeleteAdvertHandler extends RequestHandler<Boolean, DeleteAdvertCommand> {
    private final AdvertDao advertDao;

    public DeleteAdvertHandler(AdvertDao userDao) {
        this.advertDao = userDao;
    }

    @Override
    public Boolean handle(DeleteAdvertCommand request) {
        return advertDao.deleteAdvertById(request.getAdvertId());
    }
}
