package by.fpmibsu.findafriend.dataaccesslayer.advert;

import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.EntityProducer;
import by.fpmibsu.findafriend.dataaccesslayer.StatementBuilder;
import by.fpmibsu.findafriend.entity.Advert;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbAdvertDao implements AdvertDao, AutoCloseable {
    private final Logger logger = LogManager.getLogger();
    private static final String SQL_SELECT_ALL_ADVERTS = """
            SELECT * FROM advert;
            """;
    private static final String SQL_GET_USERS_ADVERTS = """
            SELECT * FROM advert
            WHERE user_id=?
            """;
    private static final String SQL_SELECT_BY_ID = """
            SELECT * FROM advert
            WHERE advert_id=?;
            """;
    private static final String SQL_INSERT_ADVERT = """
            INSERT INTO advert(advert_type, title, description, creation_date, place_id, user_id)
            VALUES (?, ?, ?, ?, ?, ?);
             """;
    private static final String SQL_UPDATE_ADVERT = """
            DECLARE @id INT = ?
            UPDATE advert
            SET title=?,
                description=?,
                creation_date=?,
                place_id=?,
                user_id=?
            WHERE advert_id = @id;
            """;
    private static final String SQL_DELETE_ADVERT_BY_USER_ID = """
            DELETE FROM advert
            WHERE user_id=?""";
    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM advert
            WHERE advert_id=?""";

    private final Connection connection;
    private final StatementBuilder statementBuilder;

    public DbAdvertDao(Connection connection) {
        this.connection = connection;
        this.statementBuilder = new StatementBuilder(connection);
    }

    @Override
    public List<Advert> getAll() throws DaoException {
        List<Advert> adverts = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ADVERTS);
            while (resultSet.next()) {
                adverts.add(EntityProducer.makeAdvert(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);


        }
        return adverts;
    }

    @Override
    public Advert getEntityById(Integer id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_SELECT_BY_ID, id);
            var resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return EntityProducer.makeAdvert(resultSet);
            }
            return null;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);


        }
    }

    @Override
    public boolean delete(Advert instance) throws DaoException {
        return delete(instance.getId());
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_BY_ID, value);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);

        }
        return true;
    }

    @Override
    public boolean create(Advert instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_INSERT_ADVERT,
                    instance.getAdvertType().getValue(),
                    instance.getTitle(),
                    instance.getDescription(),
                    instance.getCreationDate(),
                    instance.getPlace().getId(),
                    instance.getOwner().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);

        }
    }

    @Override
    public Advert update(Advert instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_UPDATE_ADVERT,
                    instance.getId(),
                    instance.getTitle(),
                    instance.getDescription(),
                    instance.getCreationDate(),
                    instance.getPlace().getId(),
                    instance.getOwner().getId());
            statement.executeUpdate();
            return instance;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);

        }
    }

    @Override
    public boolean deleteAdvertById(int id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_BY_ID, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);

        }
        return true;
    }

    @Override
    public boolean deleteByUserId(int id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_ADVERT_BY_USER_ID, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);

        }
        return true;
    }

    @Override
    public List<Advert> getUsersAdverts(int userId) throws DaoException {
        List<Advert> adverts = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_GET_USERS_ADVERTS, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                adverts.add(EntityProducer.makeAdvert(resultSet));
            }
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);

        }
        return adverts;
    }

    @Override
    public void close() throws Exception {
        close(connection);
    }
}
