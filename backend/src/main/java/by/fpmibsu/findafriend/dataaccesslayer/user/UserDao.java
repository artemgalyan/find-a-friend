package by.fpmibsu.findafriend.dataaccesslayer.user;

import by.fpmibsu.findafriend.dataaccesslayer.Dao;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.entity.User;

public interface UserDao extends Dao<Integer, User> {
    boolean deleteUserById(int id) throws DaoException;
}
