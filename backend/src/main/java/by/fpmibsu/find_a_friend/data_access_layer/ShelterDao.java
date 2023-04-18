package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ShelterDao implements ShelterDaoInterface {
    private static final String SQL_SELECT_ALL_SHELTERS = """
            SELECT shelter_id, name, place.place_id, place.country, place.region, place.city, place.district
            FROM shelter
                LEFT JOIN place ON shelter.place_id=place.place_id""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT shelter_id, name, place.place_id, place.country, place.region, place.city, place.district
            FROM shelter
                LEFT JOIN place ON shelter.place_id=place.place_id
                WHERE shelter_id=?""";
    private static final String SQL_INSERT_SHELTER = """
            INSERT INTO shelter VALUES(?,?)""";
    private static final String SQL_DELETE_SHELTER = """
            DELETE
            FROM shelter
            WHERE shelter_id=?""";

    private static final String SQL_UPDATE_SHELTER = """
            UPDATE shelter
            SET place_id=?,
                name=?
            WHERE shelter_id=?
            """;

    private static final String SQL_DELETE_BY_PLACE_SHELTER = """
            DELETE
            FROM shelter
            WHERE place_id=?
            """;

    private final StatementBuilder statementBuilder;
    private final Connection connection;

    public ShelterDao(Connection connection) {
        this.connection = connection;
        this.statementBuilder = new StatementBuilder(connection);
    }

    @Override
    public List<Shelter> getAll() throws DaoException {
        List<Shelter> shelters = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_SHELTERS);
            while (resultSet.next()) {
                shelters.add(EntityProducer.makeShelter(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return shelters;
    }

    @Override
    public Shelter getEntityById(Integer id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder
                    .prepareStatement(SQL_SELECT_BY_ID, id);
            ResultSet resultSet = statement.executeQuery();
            if (!resultSet.next()) {
                return null;
            }
            return EntityProducer.makeShelter(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public boolean delete(Shelter instance) throws DaoException {
        return delete(instance.getId());
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_SHELTER, value);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public boolean create(Shelter instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_INSERT_SHELTER,
                    instance.getPlace().getId(),
                    instance.getName());
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public Shelter update(Shelter instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_UPDATE_SHELTER,
                    instance.getPlace().getId(),
                    instance.getName(),
                    instance.getId());
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return instance;
    }

    @Override
    public boolean deleteByPlaceId(int id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_BY_PLACE_SHELTER, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }
}
