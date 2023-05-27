package by.fpmibsu.findafriend.dataaccesslayer.token;

import by.fpmibsu.findafriend.dataaccesslayer.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface TokenDao {
    boolean hasToken(String token) throws DaoException;
    void insertToken(String token) throws DaoException;

    default void close(Statement statement) {
        if (statement == null) {
            return;
        }
        try {
            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
        }
    }

    default void close(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
        }
    }
}
