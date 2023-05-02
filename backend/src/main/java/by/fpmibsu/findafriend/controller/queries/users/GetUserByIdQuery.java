package by.fpmibsu.findafriend.controller.queries.users;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.ParsingUtils;
import by.fpmibsu.findafriend.controller.models.UserModel;

import java.util.Map;
import java.util.Optional;
import java.util.OptionalInt;

public class GetUserByIdQuery extends Request<UserModel> {
    private int userId;

    public int getUserId() {
        return userId;
    }

    public GetUserByIdQuery(int userId) {
        this.userId = userId;
    }

    public static Optional<GetUserByIdQuery> tryCreate(Map<String, String> query) {
        OptionalInt id;
        if (!query.containsKey("id")) {
            return Optional.empty();
        }
        id = ParsingUtils.tryParseInt(query.get("id"));
        if (id.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(new GetUserByIdQuery(id.getAsInt()));
    }
}
