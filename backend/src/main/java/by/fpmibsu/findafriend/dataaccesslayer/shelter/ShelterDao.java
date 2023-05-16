package by.fpmibsu.findafriend.dataaccesslayer.shelter;

import by.fpmibsu.findafriend.dataaccesslayer.Dao;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.entity.Shelter;

public interface ShelterDao extends Dao<Integer, Shelter> {
    boolean deleteByPlaceId(int id) throws DaoException;
    boolean deleteShelterById(int id) throws DaoException;
}
