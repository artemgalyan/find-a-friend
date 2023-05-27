package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.application.serviceproviders.ServiceProvider;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;

import java.util.HashSet;
import java.util.List;

public class GetAnimalAdvertsByShelterIdHandler extends RequestHandler<List<AnimalAdvertModel>, GetAnimalAdvertsByShelterIdQuery> {
    private final ShelterDao shelterDao;
    private final UserShelterDao userShelterDao;
    private final AnimalAdvertDao animalAdvertDao;

    public GetAnimalAdvertsByShelterIdHandler(ShelterDao shelterDao, UserShelterDao userShelterDao, AnimalAdvertDao animalAdvertDao) {
        this.shelterDao = shelterDao;
        this.userShelterDao = userShelterDao;
        this.animalAdvertDao = animalAdvertDao;
    }

    @Override
    public List<AnimalAdvertModel> handle(GetAnimalAdvertsByShelterIdQuery request) throws Exception {
        var shelter = shelterDao.getEntityById(request.id);
        if (shelter == null) {
            return null;
        }

        var admins = new HashSet<>(
                userShelterDao.getUsersId(shelter.getId())
        );
        return animalAdvertDao.getAll()
                .stream()
                .filter(a -> admins.contains(a.getOwner().getId()))
                .map(a -> AnimalAdvertModel.of(a, request.id, shelter.getName()))
                .toList();
    }
}
