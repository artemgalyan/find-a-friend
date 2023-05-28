package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;

public class GetAnimalAdvertHandler extends RequestHandler<AnimalAdvertModel, GetAnimalAdvertQuery> {
    private final AnimalAdvertDao animalAdvertDao;
    private final UserShelterDao userShelterDao;
    private final ShelterDao shelterDao;
    private final PlaceDao placeDao;

    public GetAnimalAdvertHandler(AnimalAdvertDao animalAdvertDao, UserShelterDao userShelterDao, ShelterDao shelterDao, PlaceDao placeDao) {
        this.animalAdvertDao = animalAdvertDao;
        this.userShelterDao = userShelterDao;
        this.shelterDao = shelterDao;
        this.placeDao = placeDao;
    }

    @Override
    public AnimalAdvertModel handle(GetAnimalAdvertQuery request) throws Exception {
        var advert = animalAdvertDao.getEntityById(request.advertId);
        if (advert == null) {
            return null;
        }
        var place = placeDao.getEntityById(advert.getPlace().getId());
        advert.setPlace(place);
        var shelterId = userShelterDao.getShelterId(advert.getOwner().getId());
        if (shelterId == -1) {
            return AnimalAdvertModel.of(advert, -1, null);
        }

        var shelterName = shelterDao.getEntityById(shelterId)
                .getName();
        return AnimalAdvertModel.of(advert, shelterId, shelterName);
    }
}
