package by.fpmibsu.findafriend.dataaccesslayer;

import by.fpmibsu.findafriend.entity.Entity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

public interface Dao<K, T extends Entity> extends DaoBase {
    public final Logger logger = LogManager.getLogger(Dao.class);
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
            logger.error(e);
        }
    }

    default void close(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e);
        }
    }
}
