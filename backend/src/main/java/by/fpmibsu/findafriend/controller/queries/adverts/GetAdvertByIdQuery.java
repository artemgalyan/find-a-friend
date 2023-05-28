package by.fpmibsu.findafriend.controller.queries.adverts;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.ParsingUtils;
import by.fpmibsu.findafriend.controller.models.AdvertModel;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class GetAdvertByIdQuery extends Request<AdvertModel> {
    private int advertId;

    public int getAdvertId() {
        return advertId;
    }

    public GetAdvertByIdQuery(int advertId) {
        this.advertId = advertId;
    }

    public static Optional<GetAdvertByIdQuery> tryCreate(Map<String, String> query) {
        OptionalInt id;
        if (!query.containsKey("id")) {
            return Optional.empty();
        }
        id = ParsingUtils.tryParseInt(query.get("id"));
        if (id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new GetAdvertByIdQuery(id.getAsInt()));
    }
}