package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Entity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface Dao<K, T extends Entity> {
    List<T> getAll() throws DaoException;

    T getEntityById(K id) throws DaoException;

    boolean delete(T instance) throws DaoException;

    boolean delete(K value) throws DaoException;

    boolean create(T instance) throws DaoException;

    T update(T instance) throws DaoException;

    default void close(Statement statement) {
        if (statement == null) {
            return;
        }
        try {
            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    default void close(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
