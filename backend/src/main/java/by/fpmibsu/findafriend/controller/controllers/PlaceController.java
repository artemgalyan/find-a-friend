package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.commands.places.CreatePlaceCommand;
import by.fpmibsu.findafriend.controller.commands.places.DeletePlaceCommand;
import by.fpmibsu.findafriend.controller.commands.places.UpdatePlaceCommand;
import by.fpmibsu.findafriend.controller.queries.places.GetPlaceByIdQuery;
import by.fpmibsu.findafriend.controller.queries.places.GetPlacesQuery;
import by.fpmibsu.findafriend.entity.User;

@ControllerRoute(route = "/places")
public class PlaceController extends Controller {
    private final Mediatr mediatr;

    public PlaceController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getAllPlaces", method = HttpMethod.GET)
    public HandleResult getAll() {
        return ok(mediatr.send(new GetPlacesQuery()));
    }

    @Endpoint(path = "/getPlace", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) {
        return ok(mediatr.send(new GetPlaceByIdQuery(id)));
    }

    @RequireAuthentication
    @Endpoint(path = "/createPlace", method = HttpMethod.POST)
    public HandleResult createPlace(@FromBody CreatePlaceCommand request, @WebToken(parameterName = "role") String role) {
        if (!User.Role.ADMINISTRATOR.toString().equals(role)) {
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/updatePlace", method = HttpMethod.PUT)
    public HandleResult updatePlace(@FromBody UpdatePlaceCommand request, @WebToken(parameterName = "role") String role) {
        if (!User.Role.ADMINISTRATOR.toString().equals(role)) {
            return notAuthorized();
        }
        return ok(mediatr.send(request));
    }

    @RequireAuthentication
    @Endpoint(path = "/deletePlace", method = HttpMethod.DELETE)
    public HandleResult deletePlaceById(@FromQuery(parameterName = "id") int id, @WebToken(parameterName = "role") String role) {
        if (!User.Role.ADMINISTRATOR.toString().equals(role)) {
            return notAuthorized();
        }
        return ok(mediatr.send(new DeletePlaceCommand(id)));
    }

    @Endpoint(path = "/readPlace", method = HttpMethod.POST)
    public HandleResult handlePostReadObject(@FromBody CreatePlaceCommand request) {
        return ok(request);
    }
}

