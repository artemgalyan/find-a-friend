package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.commands.places.CreatePlaceCommand;
import by.fpmibsu.findafriend.controller.commands.places.DeletePlaceCommand;
import by.fpmibsu.findafriend.controller.commands.places.UpdatePlaceCommand;
import by.fpmibsu.findafriend.controller.queries.places.GetPlaceByIdQuery;
import by.fpmibsu.findafriend.controller.queries.places.GetPlacesQuery;

@ControllerRoute(route = "/places")
public class PlaceController extends Controller {
    private final Mediatr mediatr;

    public PlaceController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getAllPlaces", method = HttpMethod.GET)
    public HandleResult getAll() {
        return Ok(mediatr.send(new GetPlacesQuery()));
    }

    @Endpoint(path = "/getPlace", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) {
        return Ok(mediatr.send(new GetPlaceByIdQuery(id)));
    }

    @Endpoint(path = "/createPlace", method = HttpMethod.POST)
    public HandleResult createPlace(@FromBody CreatePlaceCommand request) {
        return Ok(mediatr.send(request));
    }

    @Endpoint(path = "/updatePlace", method = HttpMethod.PUT)
    public HandleResult updatePlace(@FromBody UpdatePlaceCommand request) {
        return Ok(mediatr.send(request));
    }

    @Endpoint(path = "/deletePlace", method = HttpMethod.DELETE)
    public HandleResult deletePlaceById(@FromQuery(parameterName = "id") int id) {
        return Ok(mediatr.send(new DeletePlaceCommand(id)));
    }

    @Endpoint(path = "/readPlace", method = HttpMethod.POST)
    public HandleResult handlePostReadObject(@FromBody CreatePlaceCommand request) {
        return Ok(request);
    }
}

