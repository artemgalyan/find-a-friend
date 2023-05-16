package by.fpmibsu.findafriend.controller.queries.places;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.ParsingUtils;
import by.fpmibsu.findafriend.controller.models.PlaceModel;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class GetPlaceByIdQuery extends Request<PlaceModel> {
    private int placeId;

    public int getPlaceId() {
        return placeId;
    }

    public GetPlaceByIdQuery(int placeId) {
        this.placeId = placeId;
    }

    public static Optional<by.fpmibsu.findafriend.controller.queries.places.GetPlaceByIdQuery> tryCreate(Map<String, String> query) {
        OptionalInt id;
        if (!query.containsKey("id")) {
            return Optional.empty();
        }
        id = ParsingUtils.tryParseInt(query.get("id"));
        if (id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new by.fpmibsu.findafriend.controller.queries.places.GetPlaceByIdQuery(id.getAsInt()));
    }
}
