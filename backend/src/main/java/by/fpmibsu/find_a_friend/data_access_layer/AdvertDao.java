package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Advert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AdvertDao implements AdvertDaoInterface {
    private static final String SQL_SELECT_ALL_ADVERTS = """
            EXECUTE SelectAllAdverts
            """;
    private static final String SQL_SELECT_BY_ID = """
            EXECUTE SelectAdvertById ?
            """;
    private static final String SQL_INSERT_ADVERT = """
            EXECUTE InsertAdvert ?, ?, ?, ?, ?, ?, ?, ?, ?;
             """;
    private static final String SQL_UPDATE_ADVERT = """
            EXECUTE UpdateAdvert ?, ?, ?, ?, ?, ?, ?, ?
            """;
    private static final String SQL_DELETE_ADVERT_BY_USER_ID = """
            EXECUTE DeleteAdvertByUserId ?""";
    private static final String SQL_DELETE_BY_ID = """
            EXECUTE DeleteAdvertById ?""";

    private final Connection connection;
    private final StatementBuilder statementBuilder;

    public AdvertDao(Connection connection) {
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
            statement = statementBuilder
                    .prepareStatement(SQL_SELECT_BY_ID, id);
            var resultSet = statement.executeQuery();
            return EntityProducer.makeAdvert(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
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
            var place = instance.getPlace();
            statement = statementBuilder.prepareStatement(SQL_INSERT_ADVERT,
                    instance.getAdvertType().getValue(),
                    instance.getTitle(),
                    instance.getDescription(),
                    instance.getCreationDate(),
                    place.getCountry(),
                    place.getRegion(),
                    place.getCity(),
                    place.getDistrict(),
                    instance.getOwner().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public Advert update(Advert instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            var place = instance.getPlace();
            statement = statementBuilder.prepareStatement(SQL_INSERT_ADVERT,
                    instance.getId(),
                    instance.getTitle(),
                    instance.getDescription(),
                    instance.getCreationDate(),
                    place.getCountry(),
                    place.getRegion(),
                    place.getCity(),
                    place.getDistrict());
            statement.executeUpdate();
            return instance;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean deleteByUserId(int id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_ADVERT_BY_USER_ID, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }
}
