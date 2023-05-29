package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.AuthUtils;
import by.fpmibsu.findafriend.controller.Logging;
import by.fpmibsu.findafriend.controller.commands.shelters.CreateShelterCommand;
import by.fpmibsu.findafriend.controller.commands.shelters.DeleteShelterCommand;
import by.fpmibsu.findafriend.controller.commands.shelters.UpdateShelterCommand;
import by.fpmibsu.findafriend.controller.queries.shelters.GetShelterByIdQuery;
import by.fpmibsu.findafriend.controller.queries.shelters.GetSheltersQuery;
import by.fpmibsu.findafriend.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ControllerRoute(route = "/shelters")
public class ShelterController extends Controller {
    private static final Logger logger = LogManager.getLogger(ShelterController.class);
    private final Mediatr mediatr;

    public ShelterController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getAll", method = HttpMethod.GET)
    public HandleResult getAll() throws Exception {
        return ok(mediatr.send(new GetSheltersQuery()));
    }

    @Endpoint(path = "/getById", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) throws Exception {
        return ok(mediatr.send(new GetShelterByIdQuery(id)));
    }

    @RequireAuthentication
    @Endpoint(path = "/create", method = HttpMethod.POST)
    public HandleResult createShelter(@FromBody CreateShelterCommand request, @WebToken(parameterName = "role") String role) throws Exception {
        if (!AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR)) {
            Logging.warnNonAuthorizedAccess(this.request, logger);
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/update", method = HttpMethod.PUT)
    public HandleResult updateShelter(@FromBody UpdateShelterCommand request,
                                      @WebToken(parameterName = "role") String role,
                                      @WebToken(parameterName = "shelter_id") int shelterId) throws Exception {
        if (User.Role.SHELTER_ADMINISTRATOR.toString().equals(role)) {
            if (shelterId != request.shelterId) {
                Logging.warnNonAuthorizedAccess(this.request, logger);
                return notAuthorized();
            }
        } else if (!AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR, User.Role.MODERATOR)) {
            Logging.warnNonAuthorizedAccess(this.request, logger);
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/delete", method = HttpMethod.DELETE)
    public HandleResult deleteShelterById(@FromQuery(parameterName = "id") int id, @WebToken(parameterName = "role") String role) throws Exception {
        if (!AuthUtils.allowRoles(role, User.Role.ADMINISTRATOR)) {
            Logging.warnNonAuthorizedAccess(this.request, logger);
            return notAuthorized();
        }
        return ok(mediatr.send(new DeleteShelterCommand(id)));
    }
}
