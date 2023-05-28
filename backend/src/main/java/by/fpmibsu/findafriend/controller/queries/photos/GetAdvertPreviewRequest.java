package by.fpmibsu.findafriend.controller.queries.photos;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.models.PhotoModel;

public class GetAdvertPreviewRequest extends Request<PhotoModel> {
    public int advertId;

    public GetAdvertPreviewRequest(int advertId) {
        this.advertId = advertId;
    }
}
