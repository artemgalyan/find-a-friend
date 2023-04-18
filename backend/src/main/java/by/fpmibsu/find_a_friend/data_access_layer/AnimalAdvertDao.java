package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.AnimalAdvert;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AnimalAdvertDao implements AnimalAdvertDaoInterface {
    private static final String SQL_SELECT_ALL_ANIMALADVERTS = """
            SELECT *
            FROM animal_advert
            LEFT JOIN animal_type a on a.animal_type_id = animal_advert.animal_type_id
            INNER JOIN place p on animal_advert.place_id = p.place_id""";

    private static final String SQL_SELECT_USERS_ADVERTS = """
                SELECT *
                    FROM animal_advert
                    LEFT JOIN animal_type a on a.animal_type_id = animal_advert.animal_type_id
                    INNER JOIN place p on animal_advert.place_id = p.place_id
                WHERE user_id=?
            """;
    private static final String SQL_SELECT_BY_ID = """
            SELECT *
            FROM animal_advert
            LEFT JOIN animal_type a on a.animal_type_id = animal_advert.animal_type_id
            INNER JOIN place p on animal_advert.place_id = p.place_id
            WHERE animal_advert_id=?""";
    private static final String SQL_INSERT_ANIMALADVERT = """
            DECLARE @type_id INT = (
              SELECT animal_type_id FROM animal_type WHERE name=?
            );
            INSERT INTO animal_advert(title, description, creation_date, birthday,
             sex, castrated, animal_type_id, user_id, place_id)
            VALUES (?, ?, ?, ?, ?, ?, @type_id, ?, ?)""";
    private static final String SQL_DELETE_ANIMALADVERT = """
            DELETE FROM animal_advert
            WHERE animal_advert_id=?""";
    private static final String SQL_DELETE_BY_USER_ID = """
            DELETE FROM animal_advert
            WHERE user_id=?""";

    private static final String SQL_UPDATE_ANIMALADVERT = """
            DECLARE @adv_id INT = ?
            DECLARE @type_id INT = (
              SELECT animal_type_id FROM animal_type WHERE name=?
            );
            UPDATE animal_advert
            SET title=?,
                description=?,
                creation_date=?,
                birthday=?,
                sex=?,
                castrated=?,
                animal_type_id=@type_id,
                user_id=?,
                place_id=?
            WHERE animal_advert_id=?
            """;

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
            statement = statementBuilder.prepareStatement(SQL_INSERT_ANIMALADVERT,
                    instance.getAnimalType(),
                    instance.getTitle(),
                    instance.getDescription(),
                    instance.getCreationDate(),
                    instance.getBirthdate(),
                    instance.getSex().getValue(),
                    instance.isCastrated(),
                    instance.getOwner().getId(),
                    instance.getPlace().getId());
            statement.executeUpdate();
            return true;
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
    }

    @Override
    public  AnimalAdvert update(AnimalAdvert instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_UPDATE_ANIMALADVERT,
                    instance.getId(),
                    instance.getAnimalType(),
                    instance.getTitle(),
                    instance.getDescription(),
                    instance.getCreationDate(),
                    instance.getBirthdate(),
                    instance.getSex().getValue(),
                    instance.isCastrated(),
                    instance.getOwner().getId(),
                    instance.getPlace().getId());
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

    @Override
    public List<AnimalAdvert> getUsersAdverts(int userId) throws DaoException {
        List<AnimalAdvert> adverts = new ArrayList<>();
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_SELECT_USERS_ADVERTS, userId);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                adverts.add(EntityProducer.makeAnimalAdvert(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return adverts;
    }
}
