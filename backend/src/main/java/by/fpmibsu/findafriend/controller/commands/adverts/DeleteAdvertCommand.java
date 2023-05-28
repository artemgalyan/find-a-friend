package by.fpmibsu.findafriend.controller.commands.adverts;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.ParsingUtils;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class DeleteAdvertCommand extends Request<Boolean> {
    private int advertId;

    public DeleteAdvertCommand(int advertId) {
        this.advertId = advertId;
    }

    public int getAdvertId() {
        return advertId;
    }

    public static Optional<DeleteAdvertCommand> tryCreate(Map<String, String> query) {
        OptionalInt id;
        if (!query.containsKey("id")) {
            return Optional.empty();
        }
        id = ParsingUtils.tryParseInt(query.get("id"));
        if (id.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new DeleteAdvertCommand(id.getAsInt()));
    }
}
