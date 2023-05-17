package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.commands.shelters.CreateShelterCommand;
import by.fpmibsu.findafriend.controller.commands.shelters.DeleteShelterCommand;
import by.fpmibsu.findafriend.controller.commands.shelters.UpdateShelterCommand;
import by.fpmibsu.findafriend.controller.queries.shelters.GetShelterByIdQuery;
import by.fpmibsu.findafriend.controller.queries.shelters.GetSheltersQuery;

@ControllerRoute(route = "/shelters")
public class ShelterController extends Controller {
    private final Mediatr mediatr;

    public ShelterController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getAllShelters", method = HttpMethod.GET)
    public HandleResult getAll() {
        return Ok(mediatr.send(new GetSheltersQuery()));
    }

    @Endpoint(path = "/getShelter", method = HttpMethod.GET)
    public HandleResult getById(@FromQuery(parameterName = "id") int id) {
        return Ok(mediatr.send(new GetShelterByIdQuery(id)));
    }

    @Endpoint(path = "/createShelter", method = HttpMethod.POST)
    public HandleResult createShelter(@FromBody CreateShelterCommand request) {
        return Ok(mediatr.send(request));
    }

    @Endpoint(path = "/updateShelter", method = HttpMethod.PUT)
    public HandleResult updateShelter(@FromBody UpdateShelterCommand request) {
        return Ok(mediatr.send(request));
    }

    @Endpoint(path = "/deleteShelter", method = HttpMethod.DELETE)
    public HandleResult deleteShelterById(@FromQuery(parameterName = "id") int id) {
        return Ok(mediatr.send(new DeleteShelterCommand(id)));
    }

    @Endpoint(path = "/readShelter", method = HttpMethod.POST)
    public HandleResult handlePostReadObject(@FromBody CreateShelterCommand request) {
        return Ok(request);
    }
}
