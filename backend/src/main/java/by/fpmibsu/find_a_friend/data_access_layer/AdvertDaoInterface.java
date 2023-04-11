package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Advert;
import by.fpmibsu.find_a_friend.entity.Entity;

public interface AdvertDaoInterface extends Dao<Integer, Advert> {
    boolean deleteByUserId(int id);
}
