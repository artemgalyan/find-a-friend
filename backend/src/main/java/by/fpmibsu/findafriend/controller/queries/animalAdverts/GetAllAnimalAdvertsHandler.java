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
import java.util.Map;

public class GetAllAnimalAdvertsHandler extends RequestHandler<List<AnimalAdvertModel>, GetAllAnimalAdvertsQuery> {
    private final AnimalAdvertDao animalAdvertDao;
    private final UserShelterDao userShelterDao;
    private final ShelterDao shelterDao;
    private final PlaceDao placeDao;

    public GetAllAnimalAdvertsHandler(AnimalAdvertDao animalAdvertDao, UserShelterDao userShelterDao, ShelterDao shelterDao, PlaceDao placeDao) {
        this.animalAdvertDao = animalAdvertDao;
        this.userShelterDao = userShelterDao;
        this.shelterDao = shelterDao;
        this.placeDao = placeDao;
    }

    @Override
    public List<AnimalAdvertModel> handle(GetAllAnimalAdvertsQuery request) throws Exception {
        var places = new HashMap<Integer, Place>();
        Map<Integer, Integer> userToShelter = new HashMap<>();
        Map<Integer, String> shelterToName = new HashMap<>();

        placeDao.getAll().forEach(p -> places.put(p.getId(), p));
        userShelterDao.getAll().forEach(p -> userToShelter.put(p.getUserId(), p.getShelterId()));
        shelterDao.getAll().forEach(s -> shelterToName.put(s.getId(), s.getName()));

        return animalAdvertDao.getAll()
                .stream()
                .map(a -> {
                    var place = places.get(a.getPlace().getId());
                    a.setPlace(place);
                    int shelterId = -1;
                    String shelterName = null;
                    if (userToShelter.containsKey(a.getOwner().getId())) {
                        shelterId = userToShelter.get(a.getOwner().getId());
                        shelterName = shelterToName.get(shelterId);
                    }
                    return AnimalAdvertModel.of(a, shelterId, shelterName);
                }).toList();
    }
}
