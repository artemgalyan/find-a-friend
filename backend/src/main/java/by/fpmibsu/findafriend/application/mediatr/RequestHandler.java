package by.fpmibsu.findafriend.application.mediatr;

import by.fpmibsu.findafriend.dataaccesslayer.DaoException;

import java.sql.SQLException;

public abstract class RequestHandler<T, R extends Request<? super T>> {
    public abstract T handle(R request);
}
