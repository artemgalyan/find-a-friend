package by.fpmibsu.findafriend.dataaccesslayer.validtokens;

import by.fpmibsu.findafriend.dataaccesslayer.DaoBase;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface ValidTokensDao extends DaoBase {
    final static Logger logger = LogManager.getLogger(ValidTokensDao.class);
    boolean isValidToken(String token, int userId) throws DaoException;
    void invalidateTokens(int userId) throws DaoException;
    void addValidToken(String token, int userId);

    default void close(Statement statement) {
        if (statement == null) {
            return;
        }
        try {
            if (!statement.isClosed()) {
                statement.close();
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }

    default void close(Connection connection) {
        if (connection == null) {
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            logger.error(e.getMessage());
        }
    }
}
