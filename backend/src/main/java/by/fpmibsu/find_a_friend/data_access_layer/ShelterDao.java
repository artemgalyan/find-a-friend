package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Shelter;

import java.util.List;

public class ShelterDao implements ShelterDaoInterface {
    private static final String SQL_SELECT_ALL_SHELTERS = """
            SELECT shelter_id, name, place.place_id
            FROM user
                LEFT JOIN place USING(place_id)""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT shelter_id, name, place.place_id
            FROM user
                LEFT JOIN place USING(place_id)
                WHERE place_id=?""";
    private static final String SQL_INSERT_SHELTER = """
            INSERT INTO shelter VALUES(?,?,?)""";
    private static final String SQL_DELETE_SHELTER = """
            DELETE
            FROM shelter
            WHERE shelter_id=?""";

    @Override
    public List<Shelter> getAll() throws DaoException {
        return null;
    }

    @Override
    public Shelter getEntityById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Shelter instance) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Shelter instance) throws DaoException {
        return false;
    }

    @Override
    public Shelter update(Shelter instance) throws DaoException {
        return null;
    }

    @Override
    public boolean deleteByPlaceId(int id) {
        return false;
    }
}
