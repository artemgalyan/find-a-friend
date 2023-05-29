package by.fpmibsu.findafriend.dataaccesslayer.validtokens;

import by.fpmibsu.findafriend.dataaccesslayer.DaoException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DbValidTokensDao implements ValidTokensDao {
    private final String SQL_DELETE_TOKENS = """
                DELETE
                FROM valid_tokens
                WHERE user_id=?
            """;

    private final String SQL_SELECT_TOKEN = """
            SELECT *
            FROM valid_tokens
            WHERE token=? AND user_id=?
            """;

    private final String SQL_INSERT_TOKEN = """
            INSERT INTO valid_tokens VALUES(?, ?)""";
    private final Connection connection;

    public DbValidTokensDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public boolean isValidToken(String token, int userId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_TOKEN);
            statement.setString(1, token);
            statement.setInt(2, userId);
            var result = statement.executeQuery();
            return result.next();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public void invalidateTokens(int userId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_TOKENS);
            statement.setInt(1, userId);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public void addValidToken(String token, int userId) {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_TOKEN);
            statement.setString(1, token);
            statement.setInt(2, userId);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }
}
