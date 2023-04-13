package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Place;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceDao implements PlaceDaoInterface {
    private static final String SQL_SELECT_ALL_PLACES = """
            SELECT place_id, country, region, city, district
            FROM place""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT place_id, country, region, city, district
            FROM place WHERE place_id=?""";
    private static final String SQL_INSERT_PLACE = """
            INSERT INTO place VALUES(?,?,?,?)""";
    private static final String SQL_DELETE_BY_ID = """
            DELETE FROM place
            WHERE place_id=?""";
    private static final String SQL_UPDATE = """
            UPDATE place
            SET country=?,
                region=?,
                city=?,
                district=?
            WHERE place_id=?
            """;
    private final StatementBuilder statementBuilder;
    private final Connection connection;

    public PlaceDao(Connection connection) {
        this.connection = connection;
        this.statementBuilder = new StatementBuilder(connection);
    }

    @Override
    public List<Place> getAll() throws DaoException {
        List<Place> places = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PLACES);
            while (resultSet.next()) {
                places.add(EntityProducer.makePlace(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
        return places;
    }

    @Override
    public Place getEntityById(Integer id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder
                    .prepareStatement(SQL_SELECT_BY_ID, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return EntityProducer.makePlace(resultSet);
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean delete(Place instance) throws DaoException {
        return deleteByPlaceId(instance.getId());
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
    public boolean create(Place instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_INSERT_PLACE,
                    instance.getCountry(),
                    instance.getRegion(),
                    instance.getCity(),
                    instance.getDistrict());
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public Place update(Place instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_UPDATE,
                    instance.getCountry(),
                    instance.getRegion(),
                    instance.getCity(),
                    instance.getDistrict());
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return instance;
    }
}
