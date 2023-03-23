package by.fpmibsu.find_a_friend.utils;

public abstract class RequestHandler<T extends Request, R extends Result<? extends T>> {
    public abstract R handle(T request);
}
