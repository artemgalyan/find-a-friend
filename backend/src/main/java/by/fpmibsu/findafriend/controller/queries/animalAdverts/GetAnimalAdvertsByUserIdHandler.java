package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;

import java.util.List;

public class GetAnimalAdvertsByUserIdHandler extends RequestHandler<List<AnimalAdvertModel>, GetAnimalAdvertsByUserIdQuery> {
    private final AnimalAdvertDao animalAdvertDao;
    private final UserShelterDao userShelterDao;
    private final ShelterDao shelterDao;

    public GetAnimalAdvertsByUserIdHandler(AnimalAdvertDao animalAdvertDao, UserShelterDao userShelterDao, ShelterDao shelterDao) {
        this.animalAdvertDao = animalAdvertDao;
        this.userShelterDao = userShelterDao;
        this.shelterDao = shelterDao;
    }

    @Override
    public List<AnimalAdvertModel> handle(GetAnimalAdvertsByUserIdQuery request) throws Exception {
        var shelterId = userShelterDao.getShelterId(request.userId);
        String shelterName = shelterId == -1
                ? null
                : shelterDao.getEntityById(shelterId).getName();
        return animalAdvertDao.getUsersAdverts(request.userId)
                .stream().map(a -> AnimalAdvertModel.of(a, shelterId, shelterName))
                .toList();

    }
}
