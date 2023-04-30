package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.AnimalAdvert;

import java.util.List;

public interface AnimalAdvertDao extends Dao<Integer, AnimalAdvert> {
    boolean deleteByUserId(int id) throws DaoException;
    List<AnimalAdvert> getUsersAdverts(int userId) throws DaoException;
}
