package by.fpmibsu.findafriend.application.mediatr;

public abstract class RequestHandler<T, R extends Request<? super T>> {
    public abstract T handle(R request) throws Exception;
}
