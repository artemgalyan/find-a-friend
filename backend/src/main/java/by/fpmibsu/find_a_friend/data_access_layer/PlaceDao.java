package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Entity;
import by.fpmibsu.find_a_friend.entity.Place;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PlaceDao implements Dao<Integer, Place> {
    private static final String SQL_SELECT_ALL_PLACES = "SELECT place_id, country, region, city, district FROM dbo.place";
    private static final String SQL_SELECT_BY_ID = "SELECT place_id, country, region, city, district FROM place WHERE place_id=?";
    private static final String SQL_INSERT_PLACE = "INSERT INTO place VALUES(?,?,?,?,?)";
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
        return null;
    }

    @Override
    public boolean delete(Place instance) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Place instance) throws DaoException {
        return false;
    }

    @Override
    public Place update(Place instance) throws DaoException {
        return null;
    }
}
