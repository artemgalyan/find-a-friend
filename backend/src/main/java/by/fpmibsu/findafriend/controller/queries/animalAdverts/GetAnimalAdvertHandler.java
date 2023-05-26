package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.application.serviceproviders.ServiceProvider;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;

public class GetAnimalAdvertHandler extends RequestHandler<AnimalAdvertModel, GetAnimalAdvertQuery> {
    private final AnimalAdvertDao animalAdvertDao;
    private final ServiceProvider serviceProvider;

    public GetAnimalAdvertHandler(AnimalAdvertDao animalAdvertDao, ServiceProvider serviceProvider) {
        this.animalAdvertDao = animalAdvertDao;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public AnimalAdvertModel handle(GetAnimalAdvertQuery request) throws Exception {
        var advert = animalAdvertDao.getEntityById(request.advertId);
        if (advert == null) {
            return null;
        }
        var userShelterDao = serviceProvider.getRequiredService(UserShelterDao.class);
        var shelterId = userShelterDao.getShelterId(advert.getOwner().getId());
        if (shelterId == -1) {
            return AnimalAdvertModel.of(advert, -1, null);
        }
        var shelterDao = serviceProvider.getRequiredService(ShelterDao.class);
        var shelterName = shelterDao.getEntityById(shelterId)
                .getName();
        return AnimalAdvertModel.of(advert, shelterId, shelterName);
    }
}
