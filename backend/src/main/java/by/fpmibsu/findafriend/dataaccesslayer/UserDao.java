package by.fpmibsu.findafriend.dataaccesslayer;

import by.fpmibsu.findafriend.entity.User;

public interface UserDao extends Dao<Integer, User> {
    boolean deleteUserById(int id) throws DaoException;
}
