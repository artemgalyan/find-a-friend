package by.fpmibsu.find_a_friend.application;

public class DuplicateEndpointException extends RuntimeException {
    public DuplicateEndpointException() {
    }

    public DuplicateEndpointException(String message) {
        super(message);
    }

    public DuplicateEndpointException(String message, Throwable cause) {
        super(message, cause);
    }

    public DuplicateEndpointException(Throwable cause) {
        super(cause);
    }

    public DuplicateEndpointException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
