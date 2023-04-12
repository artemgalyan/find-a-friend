package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Advert;
import by.fpmibsu.find_a_friend.entity.Photo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhotoDao implements PhotoDaoInterface {
    private static final String SQL_SELECT_ALL_PHOTO = """
            SELECT photo_id, data, animal_advert_id.place_id
            FROM photo
                LEFT JOIN animal_advert ON photo.animal_advert_id = animal_advert.animal_advert_id""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT photo_id, data, animal_advert_id.place_id
            FROM photo
                LEFT JOIN animal_advert ON animal_advert.animal_advert_id = photo.animal_advert_id
                WHERE animal_advert_id=?""";
    private static final String SQL_INSERT_PHOTO = """
            INSERT INTO shelter VALUES(?)""";
    private static final String SQL_DELETE_PHOTO = """
            DELETE
            FROM photo
            WHERE photo_id=?""";
    private static final String SQL_DELETE_PHOTO_BY_ANIMAL_ADVERT_ID = """
            EXECUTE DeletePhotoByAnimalAdvertId ?""";
    private static final String SQL_DELETE_BY_ID = """
            EXECUTE DeletePhotoById ?""";
    private static final String SQL_UPDATE = """
            UPDATE photo
            SET data=?,
            WHERE photo_id=?
            """;
    private final Connection connection;
    private final StatementBuilder statementBuilder;

    public PhotoDao(Connection connection) {
        this.connection = connection;
        this.statementBuilder = new StatementBuilder(connection);
    }
    @Override
    public List<Photo> getAll() throws DaoException {
        List<Photo> photo = new ArrayList<>();
        Statement statement = null;
        try {
            statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(SQL_SELECT_ALL_PHOTO);
            while (resultSet.next()) {
                photo.add(EntityProducer.makePhoto(resultSet));
            }
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
        return photo;
    }

    @Override
    public Photo getEntityById(Integer id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder
                    .prepareStatement(SQL_SELECT_BY_ID, id);
            var resultSet = statement.executeQuery();
            return EntityProducer.makePhoto(resultSet);
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(connection);
        }
    }

    @Override
    public boolean delete(Photo instance) throws DaoException {
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
    public boolean create(Photo instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_INSERT_PHOTO,
                    instance.getData());
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException("", e);
        } finally {
            close(statement);
        }
        return true;
    }

    @Override
    public Photo update(Photo instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_UPDATE,
                    instance.getData());
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return instance;
    }

    @Override
    public boolean deleteByAnimalAdvertId(int id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_PHOTO_BY_ANIMAL_ADVERT_ID, id);
            int result = statement.executeUpdate();
        } catch (SQLException e) {
            throw new DaoException(e);
        } finally {
            close(statement);
        }
        return true;
    }
}
