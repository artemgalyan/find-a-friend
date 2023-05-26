package by.fpmibsu.findafriend.dataaccesslayer.usershelter;

import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.StatementBuilder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DbUserShelterDao implements UserShelterDao {
    private static final String SQL_ADD_CONNECTION = """
            INSERT INTO user_shelter(user_id, shelter_id) VALUES (?, ?)
            """;
    private static final String SQL_SELECT_ALL = """
            SELECT * FROM user_shelter""";
    private static final String SQL_REMOVE_BY_TWO = """
            DELETE FROM user_shelter WHERE shelter_id = ? AND user_id = ?""";
    private static final String SQL_REMOVE_SHELTER = """
            DELETE FROM user_shelter WHERE shelter_id = ?""";
    private static final String SQL_REMOVE_USER = """
            DELETE FROM user_shelter WHERE user_id = ?""";

    private static final String SQL_SELECT_USER_ID_BY_SHELTER_ID = """
            SELECT user_id
            FROM user_shelter
            WHERE shelter_id=?""";

    private static final String SQL_SELECT_SHELTER_ID_BY_USER_ID = """
            SELECT shelter_id
            FROM user_shelter
            WHERE user_id=?""";
    private final Connection connection;
    private final StatementBuilder builder;

    public DbUserShelterDao(Connection connection) {
        this.connection = connection;
        this.builder = new StatementBuilder(connection);
    }

    @Override
    public List<Pair> getAll() throws DaoException {
        var result = new ArrayList<Pair>();
        PreparedStatement statement = null;
        try {
            statement = builder.prepareStatement(SQL_SELECT_ALL);
            var set = statement.executeQuery();
            while (set.next()) {
                result.add(new Pair(set.getInt("shelter_id"), set.getInt("user_id")));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void add(int shelterId, int userId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = builder.prepareStatement(SQL_ADD_CONNECTION, userId, shelterId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void remove(int shelterId, int userId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = builder.prepareStatement(SQL_REMOVE_BY_TWO, shelterId, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void removeAll(int shelterId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = builder.prepareStatement(SQL_REMOVE_SHELTER, shelterId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public void removeUser(int userId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = builder.prepareStatement(SQL_REMOVE_USER, userId);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public List<Integer> getUsersId(int shelterId) throws DaoException {
        List<Integer> result = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = builder.prepareStatement(SQL_SELECT_USER_ID_BY_SHELTER_ID, shelterId);
            var set = statement.executeQuery();
            while (set.next()) {
                result.add(set.getInt("user_id"));
            }
            return result;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }

    @Override
    public int getShelterId(int userId) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = builder.prepareStatement(SQL_SELECT_SHELTER_ID_BY_USER_ID, userId);
            var set = statement.executeQuery();
            if (set.next()) {
                return set.getInt("shelter_id");
            }
            return -1;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            close(connection);
        }
    }
}
