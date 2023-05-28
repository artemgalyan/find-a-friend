package by.fpmibsu.findafriend.controller.queries.photos;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.PhotoModel;
import by.fpmibsu.findafriend.dataaccesslayer.photo.PhotoDao;

public class GetAdvertPreviewHandler extends RequestHandler<PhotoModel, GetAdvertPreviewRequest> {
    private final PhotoDao photoDao;

    public GetAdvertPreviewHandler(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @Override
    public PhotoModel handle(GetAdvertPreviewRequest request) throws Exception {
        return photoDao.getAdvertPhotos(request.advertId)
                .stream()
                .map(PhotoModel::of)
                .findFirst()
                .orElse(null);
    }
}
