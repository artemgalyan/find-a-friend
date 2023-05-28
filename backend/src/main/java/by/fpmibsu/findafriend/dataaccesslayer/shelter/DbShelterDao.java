package by.fpmibsu.findafriend.dataaccesslayer.shelter;

import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.EntityProducer;
import by.fpmibsu.findafriend.dataaccesslayer.StatementBuilder;
import by.fpmibsu.findafriend.entity.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DbShelterDao implements ShelterDao, AutoCloseable {
    final private Logger logger = LogManager.getLogger();
    private static final String SQL_SELECT_ALL_SHELTERS = """
            SELECT shelter_id, name, place.place_id, place.country,
                place.region, place.city, place.district, shelter.address, shelter.website
            FROM shelter
                LEFT JOIN place ON shelter.place_id=place.place_id""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT shelter_id, name, place.place_id, place.country, place.region, place.city, place.district,
                shelter.address, shelter.website
            FROM shelter
                LEFT JOIN place ON shelter.place_id=place.place_id
                WHERE shelter_id=?""";
    private static final String SQL_INSERT_SHELTER = """
            INSERT INTO shelter VALUES(?,?,?,?)""";
    private static final String SQL_DELETE_BY_ID = """
            DELETE
            FROM shelter
            WHERE shelter_id=?""";

    private static final String SQL_UPDATE_SHELTER = """
            UPDATE shelter
            SET place_id=?,
                name=?,
                website=?,
                address=?
            WHERE shelter_id=?
            """;

    private static final String SQL_DELETE_BY_PLACE_SHELTER = """
            DELETE
            FROM shelter
            WHERE place_id=?
            """;

    private final StatementBuilder statementBuilder;
    private final Connection connection;

    public DbShelterDao(Connection connection) {
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
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
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
    public boolean create(Shelter instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_INSERT_SHELTER,
                    instance.getPlace().getId(),
                    instance.getName(),
                    instance.getAddress(),
                    instance.getWebsite());
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
    public Shelter update(Shelter instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_UPDATE_SHELTER,
                    instance.getPlace().getId(),
                    instance.getName(),
                    instance.getWebsite(),
                    instance.getAddress(),
                    instance.getId());
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            logger.error(e.getMessage());
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
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public boolean deleteShelterById(int id) throws DaoException {
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
    public void close() throws Exception {
        close(connection);
    }
}
