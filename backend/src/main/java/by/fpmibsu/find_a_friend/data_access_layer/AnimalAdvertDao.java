package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.AnimalAdvert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalAdvertDao implements AnimalAdvertDaoInterface {
    private static final String SQL_SELECT_ALL_ANIMALADVERTS = """
            EXECUTE SelectAllAnimalAdverts""";
    private static final String SQL_SELECT_BY_ID = """
            EXECUTE SelectAnimalAdvertById ?""";
    private static final String SQL_INSERT_ANIMALADVERT = """
            EXECUTE InsertAnimalAdvert ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?""";
    private static final String SQL_DELETE_ANIMALADVERT = """
            EXECUTE DeleteAnimalAdvertById ?""";
    private static final String SQL_DELETE_BY_USER_ID = """
            EXECUTE DeleteAnimalAdvertByUserId ?""";

    private static final String SQL_UPDATE_ANIMALADVERT = """
            EXECUTE UpdateAnimalAdvert ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?""";

    private final Connection connection;
    private final StatementBuilder statementBuilder;

    public AnimalAdvertDao(Connection connection) {
        this.connection = connection;
        this.statementBuilder = new StatementBuilder(connection);
    }

    @Override
    public List<AnimalAdvert> getAll() throws DaoException {
        List<AnimalAdvert> adverts = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_ANIMALADVERTS);
            while (resultSet.next()) {
                adverts.add(EntityProducer.makeAnimalAdvert(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
        return adverts;
    }

    @Override
    public AnimalAdvert getEntityById(Integer id) throws DaoException {
        PreparedStatement statement;
        try {
            statement = statementBuilder
                    .prepareStatement(SQL_SELECT_BY_ID, id);
            var resultSet = statement.executeQuery();
            return EntityProducer.makeAnimalAdvert(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(AnimalAdvert instance) throws DaoException {
        return delete(instance.getId());
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_ANIMALADVERT, value);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public boolean create(AnimalAdvert instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            var place = instance.getPlace();
            Integer ownerId = null;
            if (instance.getOwner() != null) {
                ownerId = instance.getOwner().getId();
            }
            statement = statementBuilder.prepareStatement(SQL_INSERT_ANIMALADVERT,
                    instance.getTitle(),
                    instance.getDescription(),
                    instance.getCreationDate(),
                    instance.getBirthdate(),
                    instance.getSex().asChar(),
                    instance.isCastrated() ? 'T' : 'F',
                    instance.getAnimalType(),
                    instance.getOwner().getId(),
                    ownerId,
                    place.getCountry(),
                    place.getRegion(),
                    place.getCity(),
                    place.getDistrict());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public AnimalAdvert update(AnimalAdvert instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            var place = instance.getPlace();
            statement = statementBuilder.prepareStatement(SQL_UPDATE_ANIMALADVERT,
                    instance.getId(),
                    instance.getTitle(),
                    instance.getDescription(),
                    instance.getCreationDate(),
                    instance.getSex().asChar(),
                    instance.isCastrated() ? 'T' : 'F',
                    instance.getAnimalType(),
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
            statement = statementBuilder.prepareStatement(SQL_DELETE_BY_USER_ID, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }
}
