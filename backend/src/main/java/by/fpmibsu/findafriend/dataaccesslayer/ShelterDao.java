package by.fpmibsu.findafriend.dataaccesslayer;

import by.fpmibsu.findafriend.entity.Shelter;

public interface ShelterDao extends Dao<Integer, Shelter> {
    boolean deleteByPlaceId(int id) throws DaoException;
    boolean deleteShelterById(int id) throws DaoException;
}
