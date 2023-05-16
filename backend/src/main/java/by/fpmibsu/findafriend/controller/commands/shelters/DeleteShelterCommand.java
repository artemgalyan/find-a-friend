package by.fpmibsu.findafriend.controller.commands.shelters;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.ParsingUtils;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class DeleteShelterCommand extends Request<Boolean> {
    private int shelterId;

    public DeleteShelterCommand(int userId) {
        this.shelterId = userId;
    }

    public int getShelterId() {
        return shelterId;
    }

    public static Optional<DeleteShelterCommand> tryCreate(Map<String, String> query) {
        OptionalInt id;
        if (!query.containsKey("id")) {
            return Optional.empty();
        }
        id = ParsingUtils.tryParseInt(query.get("id"));
        if (id.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new DeleteShelterCommand(id.getAsInt()));
    }
}
