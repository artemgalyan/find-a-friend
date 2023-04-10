package by.fpmibsu.find_a_friend.application.mediatr;

public abstract class RequestHandler<T, R extends Request<? super T>> {
    public abstract T handle(R request);
}
