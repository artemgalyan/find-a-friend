package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Advert;
import by.fpmibsu.find_a_friend.entity.Entity;

import java.util.List;

public interface AdvertDaoInterface extends Dao<Integer, Advert> {
    boolean deleteByUserId(int id) throws DaoException;
    List<Advert> getUsersAdverts(int userId) throws DaoException;
}
