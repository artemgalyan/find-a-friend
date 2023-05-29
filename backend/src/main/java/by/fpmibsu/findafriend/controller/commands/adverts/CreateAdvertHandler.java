package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.authentication.AuthenticationData;
import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.advert.AdvertDao;
import by.fpmibsu.findafriend.entity.Advert;
import by.fpmibsu.findafriend.entity.Place;
import by.fpmibsu.findafriend.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Date;
import java.time.Instant;

public class CreateAdvertHandler extends RequestHandler<Boolean, CreateAdvertCommand> {
    private final Logger logger = LogManager.getLogger(CreateAdvertHandler.class);
    private final AdvertDao advertDao;
    private final AuthenticationData authenticationData;

    public CreateAdvertHandler(AdvertDao advertDao, AuthenticationData authenticationData) {
        this.advertDao = advertDao;
        this.authenticationData = authenticationData;
    }

    @Override
    public Boolean handle(CreateAdvertCommand request) {
        var place = new Place();
        place.setId(request.placeId);
        var u = new User();
        u.setId(Integer.parseInt(authenticationData.getClaim("id")));
        var advert = new Advert(0, request.title, request.description, Date.from(Instant.now()), place, u, Advert.AdvertType.fromValue(request.advertType));
        advertDao.create(advert);
        return true;
    }
}
