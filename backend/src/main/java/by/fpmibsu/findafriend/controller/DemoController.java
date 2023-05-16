package by.fpmibsu.findafriend.controller;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;

@ControllerRoute(route = "/demo")
public class DemoController extends Controller {
    public static class DemoRequest {
        public int id;
        public String value;
    }
    @Endpoint(path = "/post", method = HttpMethod.POST)
    public HandleResult handlePost(@FromBody String s) {
        return Ok(s);
    }

    @Endpoint(path = "/get", method = HttpMethod.GET)
    public HandleResult handleGet() {
        return Ok("Hi!");
    }

    @Endpoint(path = "/readObject", method = HttpMethod.POST)
    public HandleResult handlePostReadObject(@FromBody DemoRequest request) {
        return Ok(request);
    }
}
