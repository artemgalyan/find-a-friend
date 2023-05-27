package by.fpmibsu.findafriend.dataaccesslayer.token;

import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbTokenDao implements TokenDao {
    private final static Logger logger = LogManager.getLogger(DbTokenDao.class);
    private final Connection connection;

    public DbTokenDao(Connection connection) {
        this.connection = connection;
    }

    private static final String SQL_HAS_TOKEN = """
            SELECT *
            FROM auth_invalid_tokens
            WHERE [token] = ?;
            """;

    private static final String SQL_INSERT_TOKEN = """
            INSERT INTO auth_invalid_tokens([token])
            VALUES (?);
            """;

    @Override
    public boolean hasToken(String token) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_HAS_TOKEN);
            statement.setString(1, token);
            var resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void insertToken(String token) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_TOKEN);
            statement.setString(1, token);
            statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}
