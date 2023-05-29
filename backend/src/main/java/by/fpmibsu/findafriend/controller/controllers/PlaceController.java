package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.Logging;
import by.fpmibsu.findafriend.controller.commands.places.CreatePlaceCommand;
import by.fpmibsu.findafriend.controller.commands.places.DeletePlaceCommand;
import by.fpmibsu.findafriend.controller.commands.places.UpdatePlaceCommand;
import by.fpmibsu.findafriend.controller.queries.places.GetPlaceByIdQuery;
import by.fpmibsu.findafriend.controller.queries.places.GetPlacesQuery;
import by.fpmibsu.findafriend.entity.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ControllerRoute(route = "/places")
public class PlaceController extends Controller {
    private static final Logger logger = LogManager.getLogger(PlaceController.class);
    private final Mediatr mediatr;

    public PlaceController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getAll", method = HttpMethod.GET)
    public HandleResult getAll() throws Exception {
        return ok(mediatr.send(new GetPlacesQuery()));
    }

    @Endpoint(path = "/getById", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) throws Exception {
        return ok(mediatr.send(new GetPlaceByIdQuery(id)));
    }

    @RequireAuthentication
    @Endpoint(path = "/create", method = HttpMethod.POST)
    public HandleResult createPlace(@FromBody CreatePlaceCommand request, @WebToken(parameterName = "role") String role) throws Exception {
        if (!User.Role.ADMINISTRATOR.toString().equals(role)) {
            Logging.warnNonAuthorizedAccess(this.request, logger);
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/update", method = HttpMethod.PUT)
    public HandleResult updatePlace(@FromBody UpdatePlaceCommand request, @WebToken(parameterName = "role") String role) throws Exception {
        if (!User.Role.ADMINISTRATOR.toString().equals(role)) {
            Logging.warnNonAuthorizedAccess(this.request, logger);
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/delete", method = HttpMethod.DELETE)
    public HandleResult deletePlaceById(@FromQuery(parameterName = "id") int id, @WebToken(parameterName = "role") String role) throws Exception {
        if (!User.Role.ADMINISTRATOR.toString().equals(role)) {
            Logging.warnNonAuthorizedAccess(this.request, logger);
            return notAuthorized();
        }
        return ok(mediatr.send(new DeletePlaceCommand(id)));
    }
}

