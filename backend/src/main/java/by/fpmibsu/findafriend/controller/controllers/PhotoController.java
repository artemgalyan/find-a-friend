package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.dataaccesslayer.photo.PhotoDao;

@ControllerRoute(route = "/photos")
public class PhotoController extends Controller {
    private final PhotoDao photoDao;

    public PhotoController(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @Endpoint(path = "/getByAdvertId", method = HttpMethod.GET)
    public HandleResult getByAdvertId(@FromQuery(parameterName = "id") int advertId) {
        return ok(photoDao.getAdvertPhotos(advertId));
    }
}
