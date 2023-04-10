package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.User;
import by.fpmibsu.find_a_friend.entity.Entity;

public interface UserDaoInterface<K, T extends Entity> extends Dao<Integer, User> {
    boolean deleteByUserId(int id);
}
