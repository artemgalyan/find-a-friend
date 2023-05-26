package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.commands.animaladverts.CreateAnimalAdvertCommand;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;
import by.fpmibsu.findafriend.dataaccesslayer.animaladvert.AnimalAdvertDao;
import by.fpmibsu.findafriend.entity.User;

@ControllerRoute(route = "/animalAdverts")
public class AnimalAdvertsController extends Controller {
    private final AnimalAdvertDao animalAdvertDao;
    private final Mediatr mediatr;

    public AnimalAdvertsController(AnimalAdvertDao animalAdvertDao, Mediatr mediatr) {
        this.animalAdvertDao = animalAdvertDao;
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getAll", method = HttpMethod.GET)
    public HandleResult getAll() {
        return ok(
                animalAdvertDao.getAll()
                .stream()
                .map(AnimalAdvertModel::of)
                .toList()
        );
    }

    @Endpoint(path = "/get", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) {
        return ok(
                AnimalAdvertModel.of(animalAdvertDao.getEntityById(id))
        );
    }

    @Endpoint(path = "/delete", method = HttpMethod.DELETE)
    public HandleResult delete(@FromQuery(parameterName = "id") int id, @WebToken(parameterName = "id") int userId,
                               @WebToken(parameterName = "role") String role) {
        if (!User.Role.ADMINISTRATOR.toString().equals(role) && !User.Role.MODERATOR.equals(role)) {
            var advert = animalAdvertDao.getEntityById(id);
            if (advert.getOwner().getId() != userId) {
                return notAuthorized();
            }
        }
        animalAdvertDao.delete(id);
        return ok();
    }

    @RequireAuthentication
    @Endpoint(path = "/create", method = HttpMethod.POST)
    public HandleResult create(@FromBody CreateAnimalAdvertCommand command) {
        return ok(mediatr.send(command));
    }

    @RequireAuthentication
    @Endpoint(path = "/getByUserId", method = HttpMethod.GET)
    public HandleResult getByUserId(@FromQuery(parameterName = "id") int userId) {
        return ok(
                animalAdvertDao.getUsersAdverts(userId)
                        .stream().map(AnimalAdvertModel::of)
                        .toList()
        );
    }

    @RequireAuthentication
    @Endpoint(path = "/getMine", method = HttpMethod.GET)
    public HandleResult getMy(@WebToken(parameterName = "id") int id) {
        return getByUserId(id);
    }
}
