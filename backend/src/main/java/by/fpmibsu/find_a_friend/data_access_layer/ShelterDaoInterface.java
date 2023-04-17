package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Shelter;

public interface ShelterDaoInterface extends Dao<Integer, Shelter> {
    boolean deleteByPlaceId(int id) throws DaoException;
}
