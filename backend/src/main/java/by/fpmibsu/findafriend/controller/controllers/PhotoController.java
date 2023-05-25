package by.fpmibsu.findafriend.controller.controllers;

import by.fpmibsu.findafriend.application.HandleResult;
import by.fpmibsu.findafriend.application.controller.*;
import by.fpmibsu.findafriend.controller.models.PhotoModel;
import by.fpmibsu.findafriend.dataaccesslayer.photo.PhotoDao;

@ControllerRoute(route = "/photos")
public class PhotoController extends Controller {
    private final PhotoDao photoDao;

    public PhotoController(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @Endpoint(path = "/getByAdvertId", method = HttpMethod.GET)
    public HandleResult getByAdvertId(@FromQuery(parameterName = "id") int advertId) {
        return ok(
                photoDao.getAdvertPhotos(advertId)
                .stream()
                .map(PhotoModel::of)
                .toList()
        );
    }

    @Endpoint(path = "/getPreview", method = HttpMethod.GET)
    public HandleResult getPreview(@FromQuery(parameterName = "id") int advertId) {
        response.setContentType("data:image/jpeg;charset=utf-8;base64");
        return ok(
                photoDao.getAdvertPhotos(advertId)
                .stream()
                .map(PhotoModel::of)
                .findFirst()
                .orElse(null)
        );
    }
}
