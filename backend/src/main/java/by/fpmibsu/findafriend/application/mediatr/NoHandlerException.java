package by.fpmibsu.findafriend.application.mediatr;

public class NoHandlerException extends RuntimeException {
    public NoHandlerException() {
    }

    public NoHandlerException(String message) {
        super(message);
    }

    public NoHandlerException(String message, Throwable cause) {
        super(message, cause);
    }

    public NoHandlerException(Throwable cause) {
        super(cause);
    }

    public NoHandlerException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
