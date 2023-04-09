package by.fpmibsu.find_a_friend.application.controllers;

public class EndpointParameterException extends RuntimeException {
    public EndpointParameterException() {
    }

    public EndpointParameterException(String message) {
        super(message);
    }

    public EndpointParameterException(String message, Throwable cause) {
        super(message, cause);
    }

    public EndpointParameterException(Throwable cause) {
        super(cause);
    }

    public EndpointParameterException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
