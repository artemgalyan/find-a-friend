package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.advert.AdvertDao;
import by.fpmibsu.findafriend.entity.Advert;
import by.fpmibsu.findafriend.entity.Place;
import by.fpmibsu.findafriend.entity.User;

import java.sql.Date;
import java.time.Instant;

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
            u.setId(Integer.parseInt(authenticationData.claims().getClaimValueAsString("id")));
            var advert = new Advert(0, request.title, request.description, Date.from(Instant.now()), place, u, Advert.AdvertType.fromValue(request.advertType));
            advertDao.create(advert);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
