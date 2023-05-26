package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.application.serviceproviders.ServiceProvider;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;

import java.util.List;

public class GetAnimalAdvertsByUserIdHandler extends RequestHandler<List<AnimalAdvertModel>, GetAnimalAdvertsByUserIdQuery> {
    private final AnimalAdvertDao animalAdvertDao;
    private final UserShelterDao userShelterDao;
    private final ServiceProvider serviceProvider;

    public GetAnimalAdvertsByUserIdHandler(AnimalAdvertDao animalAdvertDao, UserShelterDao userShelterDao, ServiceProvider serviceProvider) {
        this.animalAdvertDao = animalAdvertDao;
        this.userShelterDao = userShelterDao;
        this.serviceProvider = serviceProvider;
    }

    @Override
    public List<AnimalAdvertModel> handle(GetAnimalAdvertsByUserIdQuery request) throws Exception {
        var shelterId = userShelterDao.getShelterId(request.userId);
        String shelterName;
        if (shelterId == -1) {
            shelterName = null;
        } else {
            shelterName = serviceProvider.getRequiredService(ShelterDao.class).getEntityById(shelterId).getName();
        }

        return animalAdvertDao.getUsersAdverts(request.userId)
                .stream().map(a -> AnimalAdvertModel.of(a, shelterId, shelterName))
                .toList();

    }
}
