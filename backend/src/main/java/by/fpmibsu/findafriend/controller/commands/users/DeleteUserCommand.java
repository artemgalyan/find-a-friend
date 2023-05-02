package by.fpmibsu.findafriend.controller.commands.users;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.ParsingUtils;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class DeleteUserCommand extends Request<Boolean> {
    private int userId;

    public DeleteUserCommand(int userId) {
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }

    public static Optional<DeleteUserCommand> tryCreate(Map<String, String> query) {
        OptionalInt id;
        if (!query.containsKey("id")) {
            return Optional.empty();
        }
        id = ParsingUtils.tryParseInt(query.get("id"));
        if (id.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(new DeleteUserCommand(id.getAsInt()));
    }
}
