package by.fpmibsu.findafriend.controller.queries.shelters;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.ParsingUtils;
import by.fpmibsu.findafriend.controller.models.ShelterModel;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class GetShelterByIdQuery extends Request<ShelterModel> {
    private int shelterId;

    public int getShelterId() {
        return shelterId;
    }

    public GetShelterByIdQuery(int shelterId) {
        this.shelterId = shelterId;
    }

    public static Optional<GetShelterByIdQuery> tryCreate(Map<String, String> query) {
        OptionalInt id;
        if (!query.containsKey("id")) {
            return Optional.empty();
        }
        id = ParsingUtils.tryParseInt(query.get("id"));
        if (id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new GetShelterByIdQuery(id.getAsInt()));
    }
}
