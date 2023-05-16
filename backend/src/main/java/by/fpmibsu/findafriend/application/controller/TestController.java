package by.fpmibsu.findafriend.application.controller;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.queries.users.GetUserByIdQuery;

@ControllerRoute(route = "/api/test")
public class TestController extends Controller {
    private final Mediatr mediatr;

    public TestController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/hello", method = HttpMethod.GET)
    public HandleResult handleGet(@FromQuery(parameterName = "x") int x,
                                  @FromQuery(parameterName = "y") int y) {
        return Ok(String.format("%d + %d", x, y));
    }

    @Endpoint(path = "/test2", method = HttpMethod.POST)
    public HandleResult coolMethod(@FromBody GetUserByIdQuery query,
                                   @FromQuery(parameterName = "pattern") String pattern) {
        return Ok(mediatr.send(query));
    }
}
