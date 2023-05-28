package by.fpmibsu.findafriend.controller.commands.animaladverts;

import by.fpmibsu.findafriend.application.authentication.AuthenticationData;
import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.photo.PhotoDao;
import by.fpmibsu.findafriend.entity.AnimalAdvert;
import by.fpmibsu.findafriend.entity.Photo;
import by.fpmibsu.findafriend.entity.Place;
import by.fpmibsu.findafriend.entity.User;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;

public class CreateAnimalAdvertsHandler extends RequestHandler<Boolean, CreateAnimalAdvertCommand> {
    private final AnimalAdvertDao animalAdvertDao;
    private final PhotoDao photoDao;
    private final AuthenticationData authenticationData;

    public CreateAnimalAdvertsHandler(AnimalAdvertDao animalAdvertDao, PhotoDao photoDao, AuthenticationData authenticationData) {
        this.animalAdvertDao = animalAdvertDao;
        this.photoDao = photoDao;
        this.authenticationData = authenticationData;
    }

    @Override
    public Boolean handle(CreateAnimalAdvertCommand command) throws Exception {
        int userId = Integer.parseInt(authenticationData.getClaim("id"));
        var place = new Place();
        place.setId(command.placeId);
        var u = new User();
        u.setId(userId);
        var advert = new AnimalAdvert(0, command.title, command.description, command.animalType, new ArrayList<>(),
                u, Date.from(Instant.now()), place, command.birthdate, AnimalAdvert.Sex.fromValue(command.sex), command.isCastrated);
        boolean result = animalAdvertDao.create(advert);
        if (!result) {
            return false;
        }
        var photos = command.photos.stream()
                .map(p -> new Photo(Base64.getEncoder().encode(p.getBytes()), advert.getId()))
                .toList();
        photoDao.create(photos);
        return true;
    }
}
