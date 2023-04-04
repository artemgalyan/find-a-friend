package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Entity;
import by.fpmibsu.find_a_friend.entity.Place;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceDao implements Dao {
    private static final String SQL_SELECT_ALL_PLACES = "SELECT place_id, country, region, city, district FROM place";
    private static final String SQL_SELECT_BY_ID = "SELECT place_id, country, region, city, district FROM place WHERE place_id=?";
    private static final String SQL_INSERT_PLACE = "INSERT INTO place VALUES(?,?,?,?,?)";
    private Connection connection = null;

    public PlaceDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List getAll() throws DaoException {
        List<Place> places = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PLACES);
            while (resultSet.next()) {
                Place place = new Place();
                place.setId(resultSet.getInt("place_id"));
                place.setCountry(resultSet.getString("country"));
                place.setRegion(resultSet.getString("region"));
                place.setCity(resultSet.getString("city"));
                place.setDistrict(resultSet.getString("district"));
                places.add(place);
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
            close(connection);
        }
        return places;
    }

    @Override
    public Entity getEntityById(Object id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Entity instance) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Object value) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Entity instance) throws DaoException {
        return false;
    }

    @Override
    public Entity update(Entity instance) throws DaoException {
        return null;
    }
}
