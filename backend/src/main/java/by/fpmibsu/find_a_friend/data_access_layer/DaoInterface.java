package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Entity;

import java.sql.Connection;
import java.sql.Statement;

public interface DaoInterface<K, T extends Entity> {
    List<T> getAll() throws DaoException;

    T getEntityById(K id) throws DaoException;

    boolean delete(T instance) throws DaoException;

    boolean delete(K value) throws DaoException;

    boolean create(T instance) throws DaoException;

    T update(T instance) throws DaoException;

    default void close(Statement statement);

    default void close(Connection connection);
}
