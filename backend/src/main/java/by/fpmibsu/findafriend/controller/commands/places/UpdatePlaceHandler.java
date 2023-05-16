package by.fpmibsu.findafriend.controller.commands.places;

import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.dataaccesslayer.place.PlaceDao;
import by.fpmibsu.findafriend.entity.Place;


public class UpdatePlaceHandler extends RequestHandler<Boolean, UpdatePlaceCommand> {
    private final PlaceDao placeDao;

    public UpdatePlaceHandler(PlaceDao placeDao) {
        this.placeDao = placeDao;
    }

    @Override
    public Boolean handle(UpdatePlaceCommand request) {
        Place place = placeDao.getEntityById(request.placeId);
        if (place == null) {
            return false;
        }
        if (request.country != null) {
            place.setCountry(request.country);
        }
        if (request.city != null) {
            place.setCity(request.city);
        }
        if (request.district != null) {
            place.setDistrict(request.district);
        }
        if (request.region != null) {
            place.setRegion(request.region);
        }
        placeDao.update(place);
        return true;
    }
}
