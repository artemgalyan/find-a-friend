package by.fpmibsu.findafriend.controller.commands.places;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.ParsingUtils;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class DeletePlaceCommand extends Request<Boolean> {
    private int placeId;

    public DeletePlaceCommand(int placeId) {
        this.placeId = placeId;
    }

    public int getPlaceId() {
        return placeId;
    }

    public static Optional<by.fpmibsu.findafriend.controller.commands.places.DeletePlaceCommand> tryCreate(Map<String, String> query) {
        OptionalInt id;
        if (!query.containsKey("id")) {
            return Optional.empty();
        }
        id = ParsingUtils.tryParseInt(query.get("id"));
        if (id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new by.fpmibsu.findafriend.controller.commands.places.DeletePlaceCommand(id.getAsInt()));
    }
}