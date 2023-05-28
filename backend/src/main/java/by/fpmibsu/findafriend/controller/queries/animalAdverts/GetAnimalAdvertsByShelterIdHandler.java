package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;
import by.fpmibsu.findafriend.entity.Place;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class GetAnimalAdvertsByShelterIdHandler extends RequestHandler<List<AnimalAdvertModel>, GetAnimalAdvertsByShelterIdQuery> {
    private final ShelterDao shelterDao;
    private final UserShelterDao userShelterDao;
    private final AnimalAdvertDao animalAdvertDao;
    private final PlaceDao placeDao;

    public GetAnimalAdvertsByShelterIdHandler(ShelterDao shelterDao, UserShelterDao userShelterDao, AnimalAdvertDao animalAdvertDao, PlaceDao placeDao) {
        this.shelterDao = shelterDao;
        this.userShelterDao = userShelterDao;
        this.animalAdvertDao = animalAdvertDao;
        this.placeDao = placeDao;
    }

    @Override
    public List<AnimalAdvertModel> handle(GetAnimalAdvertsByShelterIdQuery request) throws Exception {
        var shelter = shelterDao.getEntityById(request.id);
        if (shelter == null) {
            return null;
        }

        var places = new HashMap<Integer, Place>();
        placeDao.getAll().forEach(p -> places.put(p.getId(), p));
        var admins = new HashSet<>(
                userShelterDao.getUsersId(shelter.getId())
        );
        return animalAdvertDao.getAll()
                .stream()
                .filter(a -> admins.contains(a.getOwner().getId()))
                .map(a -> {
                    var place = places.get(a.getPlace().getId());
                    a.setPlace(place);
                    return AnimalAdvertModel.of(a, request.id, shelter.getName());
                })
                .toList();
    }
}
