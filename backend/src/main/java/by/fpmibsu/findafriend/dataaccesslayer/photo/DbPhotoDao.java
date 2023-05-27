package by.fpmibsu.findafriend.dataaccesslayer.photo;

import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.EntityProducer;
import by.fpmibsu.findafriend.dataaccesslayer.StatementBuilder;
import by.fpmibsu.findafriend.entity.Photo;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DbPhotoDao implements PhotoDao, AutoCloseable {
    private final Logger logger = LogManager.getLogger();
    private static final String SQL_SELECT_ALL_PHOTO = """
            SELECT photo_id, data, animal_advert_id
            FROM photo""";
    private static final String SQL_SELECT_BY_PHOTO_ID = """
            SELECT photo_id, data, animal_advert_id
            FROM photo
            WHERE photo_id=?""";
    private static final String SQL_INSERT_PHOTO = """
            INSERT INTO photo VALUES(?, ?)""";
    private static final String SQL_DELETE_PHOTO_BY_ID = """
            DELETE
            FROM photo
            WHERE photo_id=?""";

    private static final String SQL_SELECT_PHOTO_BY_ANIMAL_ADVERT_ID = """
            SELECT photo_id, data, animal_advert_id
            FROM photo
            WHERE animal_advert_id=?""";
    private static final String SQL_DELETE_PHOTO_BY_ANIMAL_ADVERT_ID = """
            DELETE
            FROM photo
            WHERE animal_advert_id=?""";
    private static final String SQL_UPDATE = """
            UPDATE photo
            SET data=?
            WHERE photo_id=?
            """;
    private static final String SQL_INSERT_MULTIPLE = """
                INSERT INTO photo VALUES         
            """;
    private final Connection connection;
    private final StatementBuilder statementBuilder;

    public DbPhotoDao(Connection connection) {
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
            logger.error(e.getMessage());
            throw new DaoException(e);
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
                    .prepareStatement(SQL_SELECT_BY_PHOTO_ID, id);
            var resultSet = statement.executeQuery();
            resultSet.next();
            return EntityProducer.makePhoto(resultSet);
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);

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
            statement = statementBuilder.prepareStatement(SQL_DELETE_PHOTO_BY_ID, value);
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
    public boolean create(Photo instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_INSERT_PHOTO,
                    instance.getData(), instance.getAdvertId());
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
    public Photo update(Photo instance) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_UPDATE,
                    instance.getData(), instance.getId());
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
    public boolean deleteByAnimalAdvertId(int id) throws DaoException {
        PreparedStatement statement = null;
        try {
            statement = statementBuilder.prepareStatement(SQL_DELETE_PHOTO_BY_ANIMAL_ADVERT_ID, id);
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
    public List<Photo> getAdvertPhotos(int advertId) throws DaoException {
        PreparedStatement statement = null;
        try {
            List<Photo> photos = new ArrayList<>();
            statement = statementBuilder
                    .prepareStatement(SQL_SELECT_PHOTO_BY_ANIMAL_ADVERT_ID, advertId);
            var resultSet = statement.executeQuery();
            while (resultSet.next()) {
                photos.add(EntityProducer.makePhoto(resultSet));
            }
            return photos;
        } catch (SQLException e) {
            logger.error(e.getMessage());
            throw new DaoException(e);
        } finally {
            close(statement);

        }
    }

    @Override
    public void create(List<Photo> photos) throws DaoException {
        PreparedStatement statement = null;
        try {
            connection.setAutoCommit(false);
            statement = connection.prepareStatement(SQL_INSERT_PHOTO);
            for (var p : photos) {
                statement.setBytes(1, p.getData());
                statement.setInt(2, p.getAdvertId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
        } catch (Exception e) {
            throw new DaoException(e);
        } finally {
            close(statement);
            try {
                connection.setAutoCommit(true);
            } catch (SQLException e) {

            }

        }
    }

    @Override
    public void close() throws Exception {
        close(connection);
    }
}
