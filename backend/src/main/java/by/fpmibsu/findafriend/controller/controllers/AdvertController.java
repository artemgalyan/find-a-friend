package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
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
//        return new HandleResult(200, mediatr.send(new GetUserByIdQuery(id)));
        return Ok(mediatr.send(new GetUserByIdQuery(id)));
    }
}