package by.fpmibsu.findafriend.controller.queries.photos;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.models.PhotoModel;

import java.util.List;

public class GetPhotosByAdvertIdQuery extends Request<List<PhotoModel>> {
    public int advertId;

    public GetPhotosByAdvertIdQuery(int advertId) {
        this.advertId = advertId;
    }
}
