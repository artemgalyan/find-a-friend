package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.AuthUtils;
import by.fpmibsu.findafriend.controller.commands.shelters.CreateShelterCommand;
import by.fpmibsu.findafriend.controller.commands.shelters.DeleteShelterCommand;
import by.fpmibsu.findafriend.controller.commands.shelters.UpdateShelterCommand;
import by.fpmibsu.findafriend.controller.queries.shelters.GetShelterByIdQuery;
import by.fpmibsu.findafriend.controller.queries.shelters.GetSheltersQuery;
import by.fpmibsu.findafriend.entity.User;

@ControllerRoute(route = "/shelters")
public class ShelterController extends Controller {
    private final Mediatr mediatr;

    public ShelterController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getAll", method = HttpMethod.GET)
    public HandleResult getAll() {
        return ok(mediatr.send(new GetSheltersQuery()));
    }

    @Endpoint(path = "/getById", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) {
        return ok(mediatr.send(new GetShelterByIdQuery(id)));
    }

    @RequireAuthentication
    @Endpoint(path = "/create", method = HttpMethod.POST)
    public HandleResult createShelter(@FromBody CreateShelterCommand request, @WebToken(parameterName = "role") String role) {
        if (!AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR)) {
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/update", method = HttpMethod.PUT)
    public HandleResult updateShelter(@FromBody UpdateShelterCommand request, @WebToken(parameterName = "role") String role) {
        if (!AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR, User.Role.MODERATOR, User.Role.SHELTER_ADMINISTRATOR)) {
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/delete", method = HttpMethod.DELETE)
    public HandleResult deleteShelterById(@FromQuery(parameterName = "id") int id, @WebToken(parameterName = "role") String role) {
        if (!AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR)) {
            return notAuthorized();
        }
        return ok(mediatr.send(new DeleteShelterCommand(id)));
    }
}
