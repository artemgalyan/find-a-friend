package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.User;

public interface UserDao extends Dao<Integer, User> {
    boolean deleteUserById(int id) throws DaoException;
}
