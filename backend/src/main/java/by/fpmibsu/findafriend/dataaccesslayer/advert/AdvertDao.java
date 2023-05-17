package by.fpmibsu.findafriend.dataaccesslayer.advert;

import by.fpmibsu.findafriend.dataaccesslayer.Dao;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.entity.Advert;

import java.util.List;

public interface AdvertDao extends Dao<Integer, Advert> {
    boolean deleteAdvertById(int id) throws DaoException;
    boolean deleteByUserId(int id) throws DaoException;
    List<Advert> getUsersAdverts(int userId) throws DaoException;
}
