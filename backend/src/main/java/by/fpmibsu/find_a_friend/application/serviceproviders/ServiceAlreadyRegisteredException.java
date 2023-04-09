package by.fpmibsu.find_a_friend.application.serviceproviders;

public class ServiceAlreadyRegisteredException extends RuntimeException {
    public ServiceAlreadyRegisteredException() {
    }

    public ServiceAlreadyRegisteredException(String message) {
        super(message);
    }

    public ServiceAlreadyRegisteredException(String message, Throwable cause) {
        super(message, cause);
    }

    public ServiceAlreadyRegisteredException(Throwable cause) {
        super(cause);
    }

    public ServiceAlreadyRegisteredException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
