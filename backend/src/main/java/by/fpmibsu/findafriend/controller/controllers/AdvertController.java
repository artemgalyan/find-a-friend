package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.commands.adverts.CreateAdvertCommand;
import by.fpmibsu.findafriend.controller.commands.adverts.DeleteAdvertCommand;
import by.fpmibsu.findafriend.controller.commands.adverts.UpdateAdvertCommand;
import by.fpmibsu.findafriend.controller.commands.places.CreatePlaceCommand;
import by.fpmibsu.findafriend.controller.commands.places.DeletePlaceCommand;
import by.fpmibsu.findafriend.controller.commands.places.UpdatePlaceCommand;
import by.fpmibsu.findafriend.controller.queries.users.GetUserByIdQuery;
import by.fpmibsu.findafriend.controller.queries.users.GetUsersQuery;

@ControllerRoute(route = "/adverts")
public class AdvertController extends Controller {
    private final Mediatr mediatr;

    public AdvertController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getAll", method = HttpMethod.GET)
    public HandleResult getAll() {
        return Ok(mediatr.send(new GetUsersQuery()));
    }

    @Endpoint(path = "/getAdvert", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) {
        return Ok(mediatr.send(new GetUserByIdQuery(id)));
    }
    @Endpoint(path = "/createAdvert", method = HttpMethod.POST)
    public HandleResult createAdvert(@FromBody CreateAdvertCommand request) {
        return Ok(mediatr.send(request));
    }

    @Endpoint(path = "/updateAdvert", method = HttpMethod.PUT)
    public HandleResult updateAdvert(@FromBody UpdateAdvertCommand request) {
        return Ok(mediatr.send(request));
    }

    @Endpoint(path = "/deleteAdvert", method = HttpMethod.DELETE)
    public HandleResult deleteAdvertById(@FromQuery(parameterName = "id") int id) {
        return Ok(mediatr.send(new DeleteAdvertCommand(id)));
    }

    @Endpoint(path = "/readAdvert", method = HttpMethod.POST)
    public HandleResult handlePostReadObject(@FromBody CreateAdvertCommand request) {
        return Ok(request);
    }
}