package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbUserDao implements UserDao {
    private static final String SQL_SELECT_ALL_USERS = """
            SELECT user_id, [user].name, surname, email, phone_number, login, password, role.role_id, role.name
            FROM [user]
                LEFT JOIN role ON [user].role_id = role.role_id""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT user_id, name, surname, email, phone_number, login, password, role.role_id, role.name
            FROM user
                LEFT JOIN role USING(role_id)
                WHERE user_id=?""";
    private static final String SQL_INSERT_USER = """
            INSERT INTO [user] VALUES(?,?,?,?,?,?,?)""";
    private static final String SQL_DELETE_USER = """
            DELETE
            FROM user
            WHERE user_id=?""";
    public static final String SQL_DELETE_BY_ID = """
            DELETE
            FROM advert
            WHERE user_id=?""";

    private static final String SQL_UPDATE = """
            UPDATE user
            SET contacts=?,
                region=?,
                city=?,
                district=?
            WHERE place_id=?
            """;
    private final StatementBuilder statementBuilder;
    private final Connection connection;

    public DbUserDao(Connection connection) {
        this.connection = connection;
        this.statementBuilder = new StatementBuilder(connection);
    }

    @Override
    public List<User> getAll() throws DaoException {
        List<User> users = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_USERS);
            while (resultSet.next()) {
                users.add(EntityProducer.makeUser(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
        return users;
    }

    @Override
    public User getEntityById(Integer id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder
                    .prepareStatement(SQL_SELECT_BY_ID, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return EntityProducer.makeUser(resultSet);
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean delete(User instance) throws DaoException {
        return deleteUserById(instance.getId());
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_BY_ID, value);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public boolean create(User instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            var contacts = instance.getContacts();
            statement = statementBuilder.prepareStatement(SQL_INSERT_USER,
                    contacts.getName(),
                    contacts.getSurname(),
                    contacts.getEmail(),
                    contacts.getPhoneNumber(),
                    instance.getLogin(),
                    instance.getPassword(),
                    1);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public User update(User instance) throws DaoException {
//        PreparedStatement statement = null;
//        try {
//            statement = statementBuilder.prepareStatement(SQL_UPDATE,
//                    instance.getContacts(),
//                    instance.getAnimalAdverts(),
//                    instance.getAdverts(),
//                    instance.getRole());
//                    instance.getLogin();
//                    instance.getPassword();
//            int result = statement.executeUpdate();
//        } catch (SQLException e) {
//            throw new DaoException(e);
//        } finally {
//            close(statement);
//        }
        return instance;
    }

    @Override
    public boolean deleteUserById(int id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_BY_ID, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }
}
