package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Photo;

import java.util.List;

public class PhotoDao implements PhotoDaoInterface {
    private static final String SQL_SELECT_ALL_PHOTO = """
            SELECT photo_id, data, animal_advert_id.place_id
            FROM photo
                LEFT JOIN animal_advert USING(animal_advert_id)""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT photo_id, data, animal_advert_id.place_id
            FROM photo
                LEFT JOIN animal_advert USING(animal_advert_id)
                WHERE animal_advert_id=?""";
    private static final String SQL_INSERT_PHOTO = """
            INSERT INTO shelter VALUES(?,?,?)""";
    private static final String SQL_DELETE_PHOTO = """
            DELETE
            FROM photo
            WHERE photo_id=?""";
    @Override
    public List<Photo> getAll() throws DaoException {
        return null;
    }

    @Override
    public Photo getEntityById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Photo instance) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Photo instance) throws DaoException {
        return false;
    }

    @Override
    public Photo update(Photo instance) throws DaoException {
        return null;
    }

    @Override
    public boolean deleteByPhotoId(int id) {
        return false;
    }
}
