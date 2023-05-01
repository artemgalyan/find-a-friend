package by.fpmibsu.findafriend.dataaccesslayer;

import by.fpmibsu.findafriend.entity.Advert;

import java.util.List;

public interface AdvertDao extends Dao<Integer, Advert> {
    boolean deleteByUserId(int id) throws DaoException;
    List<Advert> getUsersAdverts(int userId) throws DaoException;
}
