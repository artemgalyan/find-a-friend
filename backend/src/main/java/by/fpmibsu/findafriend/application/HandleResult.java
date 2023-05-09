package by.fpmibsu.findafriend.application;

import java.util.Optional;

public class HandleResult {
    private final int code;
    private final Optional<?> responseObject;

    public HandleResult(int code, Object responseObject) {
        this.code = code;
        this.responseObject = Optional.ofNullable(responseObject);
    }

    public HandleResult(int code) {
        this.code = code;
        this.responseObject = Optional.empty();
    }

    public int getCode() {
        return code;
    }

    public Optional<?> getResponseObject() {
        return responseObject;
    }
}
