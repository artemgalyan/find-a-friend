package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Entity;
import by.fpmibsu.find_a_friend.entity.Place;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceDao implements Dao<Integer, Place> {
    private static final String SQL_SELECT_ALL_PLACES = "SELECT place_id, country, region, city, district FROM place";
    private static final String SQL_SELECT_BY_ID = "SELECT place_id, country, region, city, district FROM place WHERE place_id=?";
    private static final String SQL_INSERT_PLACE = "INSERT INTO place VALUES(?,?,?,?,?)";
    private static final String SQL_DELETE_PLACE = "DELETE FROM place WHERE country=? AND region=? AND city=? AND district=?";
    public static final String SQL_DELETE_BY_ID = "DELETE FROM place WHERE place_id=?";
    private Connection connection;

    public PlaceDao(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Place> getAll() throws DaoException {
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
        }
        return places;
    }

    @Override
    public Place getEntityById(Integer id) throws DaoException {
        Place place = new Place();
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_SELECT_BY_ID);
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            resultSet.next();
            place.setId(resultSet.getInt("place_id"));
            place.setCountry(resultSet.getString("country"));
            place.setRegion(resultSet.getString("region"));
            place.setCity(resultSet.getString("city"));
            place.setDistrict(resultSet.getString("district"));
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
            close(connection);
        }
        return place;
    }

    @Override
    public boolean delete(Place instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_PLACE);
            statement.setString(1, instance.getCountry());
            statement.setString(2, instance.getRegion());
            statement.setString(3, instance.getCity());
            statement.setString(4, instance.getDistrict());
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
            close(connection);
        }
        return true;
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_DELETE_BY_ID);
            statement.setInt(1, value);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
            close(connection);
        }
        return true;
    }

    @Override
    public boolean create(Place instance) throws DaoException {

        PreparedStatement statement = null;
        try {
            statement = connection.prepareStatement(SQL_INSERT_PLACE);
            statement.setInt(1, instance.getId());
            statement.setString(2, instance.getCountry());
            statement.setString(3, instance.getRegion());
            statement.setString(4, instance.getCity());
            statement.setString(5, instance.getDistrict());
            int resultSet = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
            close(connection);
        }
        return true;
    }

    @Override
    public Place update(Place instance) throws DaoException {
        return null;
    }
}
