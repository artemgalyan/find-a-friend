package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.dataaccesslayer.shelter.ShelterDao;
import by.fpmibsu.findafriend.dataaccesslayer.usershelter.UserShelterDao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GetAllAnimalAdvertsHandler extends RequestHandler<List<AnimalAdvertModel>, GetAllAnimalAdvertsQuery> {
    private final AnimalAdvertDao animalAdvertDao;
    private final UserShelterDao userShelterDao;
    private final ShelterDao shelterDao;

    public GetAllAnimalAdvertsHandler(AnimalAdvertDao animalAdvertDao, UserShelterDao userShelterDao, ShelterDao shelterDao) {
        this.animalAdvertDao = animalAdvertDao;
        this.userShelterDao = userShelterDao;
        this.shelterDao = shelterDao;
    }

    @Override
    public List<AnimalAdvertModel> handle(GetAllAnimalAdvertsQuery request) throws Exception {
        Map<Integer, Integer> userToShelter = new HashMap<>();
        Map<Integer, String> shelterToName = new HashMap<>();

        userShelterDao.getAll().forEach(p -> userToShelter.put(p.getUserId(), p.getShelterId()));
        shelterDao.getAll().forEach(s -> shelterToName.put(s.getId(), s.getName()));

        return animalAdvertDao.getAll()
                .stream()
                .map(a -> {
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
