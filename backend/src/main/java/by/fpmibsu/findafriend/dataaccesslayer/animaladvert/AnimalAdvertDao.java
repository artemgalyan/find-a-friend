package by.fpmibsu.findafriend.dataaccesslayer.animaladvert;

import by.fpmibsu.findafriend.dataaccesslayer.Dao;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.entity.AnimalAdvert;

import java.util.List;

public interface AnimalAdvertDao extends Dao<Integer, AnimalAdvert> {
    boolean deleteByUserId(int id) throws DaoException;
    List<AnimalAdvert> getUsersAdverts(int userId) throws DaoException;
}
