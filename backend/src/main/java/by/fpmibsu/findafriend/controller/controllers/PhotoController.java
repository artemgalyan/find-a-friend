package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.controller.queries.photos.GetAdvertPreviewRequest;
import by.fpmibsu.findafriend.controller.queries.photos.GetPhotosByAdvertIdQuery;

@ControllerRoute(route = "/photos")
public class PhotoController extends Controller {
    private final Mediatr mediatr;

    public PhotoController(Mediatr mediatr) {
        this.mediatr = mediatr;
    }

    @Endpoint(path = "/getByAdvertId", method = HttpMethod.GET)
    public HandleResult getByAdvertId(@FromQuery(parameterName = "id") int advertId) {
        return ok(mediatr.send(new GetPhotosByAdvertIdQuery(advertId)));
    }

    @Endpoint(path = "/getPreview", method = HttpMethod.GET)
    public HandleResult getPreview(@FromQuery(parameterName = "id") int advertId) {
        response.setContentType("data:image/jpeg;charset=utf-8;base64");
        return ok(mediatr.send(new GetAdvertPreviewRequest(advertId)));
    }
}
