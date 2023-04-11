package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.User;
import by.fpmibsu.find_a_friend.entity.Entity;

public interface UserDaoInterface extends Dao<Integer, User> {
    boolean deleteByUserId(int id);
}
