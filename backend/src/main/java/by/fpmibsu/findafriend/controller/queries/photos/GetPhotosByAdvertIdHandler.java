package by.fpmibsu.findafriend.controller.queries.photos;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.controller.models.PhotoModel;
import by.fpmibsu.findafriend.dataaccesslayer.photo.PhotoDao;

import java.util.List;

public class GetPhotosByAdvertIdHandler extends RequestHandler<List<PhotoModel>, GetPhotosByAdvertIdQuery> {
    private final PhotoDao photoDao;

    public GetPhotosByAdvertIdHandler(PhotoDao photoDao) {
        this.photoDao = photoDao;
    }

    @Override
    public List<PhotoModel> handle(GetPhotosByAdvertIdQuery request) throws Exception {
        return photoDao.getAdvertPhotos(request.advertId)
                .stream()
                .map(PhotoModel::of)
                .toList();
    }
}
