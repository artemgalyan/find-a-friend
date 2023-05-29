package by.fpmibsu.findafriend.application.serviceproviders;

import java.util.List;

class AggregateException extends Exception {
    private final List<? extends Throwable> basket;

    public AggregateException(List<? extends Throwable> basket) {
        this.basket = basket;
    }

    public AggregateException(String message, List<? extends Throwable> basket) {
        super(message);
        this.basket = basket;
    }

    public AggregateException(String message, Throwable cause, List<? extends Throwable> basket) {
        super(message, cause);
        this.basket = basket;
    }

    public AggregateException(Throwable cause, List<? extends Throwable> basket) {
        super(cause);
        this.basket = basket;
    }

    public AggregateException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace, List<? extends Throwable> basket) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.basket = basket;
    }

    @Override
    public String toString() {
        return String.join("\n", basket.stream().map(Throwable::toString).toList()) + super.toString();
    }
}
