package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;
import by.fpmibsu.findafriend.entity.Place;

import java.util.HashMap;
import java.util.List;

public class GetAnimalAdvertsByUserIdHandler extends RequestHandler<List<AnimalAdvertModel>, GetAnimalAdvertsByUserIdQuery> {
    private final AnimalAdvertDao animalAdvertDao;
    private final UserShelterDao userShelterDao;
    private final ShelterDao shelterDao;
    private final PlaceDao placeDao;

    public GetAnimalAdvertsByUserIdHandler(AnimalAdvertDao animalAdvertDao, UserShelterDao userShelterDao, ShelterDao shelterDao, PlaceDao placeDao) {
        this.animalAdvertDao = animalAdvertDao;
        this.userShelterDao = userShelterDao;
        this.shelterDao = shelterDao;
        this.placeDao = placeDao;
    }

    @Override
    public List<AnimalAdvertModel> handle(GetAnimalAdvertsByUserIdQuery request) throws Exception {
        var shelterId = userShelterDao.getShelterId(request.userId);
        String shelterName;
        if (shelterId == -1) {
            shelterName = null;
        } else {
            shelterName = shelterDao.getEntityById(shelterId).getName();
        }

        var places = new HashMap<Integer, Place>();
        placeDao.getAll().forEach(p -> places.put(p.getId(), p));

        return animalAdvertDao.getUsersAdverts(request.userId)
                .stream().map(a -> {
                    var place = places.get(a.getPlace().getId());
                    a.setPlace(place);
                    return AnimalAdvertModel.of(a, shelterId, shelterName);
                })
                .toList();

    }
}
