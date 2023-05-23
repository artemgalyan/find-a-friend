package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.advert.AdvertDao;
import by.fpmibsu.findafriend.entity.Advert;
import by.fpmibsu.findafriend.entity.Place;
import by.fpmibsu.findafriend.entity.User;

public class CreateAdvertHandler extends RequestHandler<Boolean, CreateAdvertCommand> {
    private final AdvertDao advertDao;
    private final Application.AuthenticationData authenticationData;

    public CreateAdvertHandler(AdvertDao advertDao, Application.AuthenticationData authenticationData) {
        this.advertDao = advertDao;
        this.authenticationData = authenticationData;
    }

    @Override
    public Boolean handle(CreateAdvertCommand request) {
        try {
            var place = new Place();
            place.setId(request.placeId);
            var u = new User();
            u.setId(authenticationData.claims().getClaimValue("id", int.class));
            var advert = new Advert(0, request.title, request.description, request.creationDate, place, u, request.advertType);
            advertDao.create(advert);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
