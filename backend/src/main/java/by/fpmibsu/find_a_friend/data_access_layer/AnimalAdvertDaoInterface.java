package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.AnimalAdvert;
import by.fpmibsu.find_a_friend.entity.Entity;

public interface AnimalAdvertDaoInterface<K, T extends Entity> extends Dao<Integer, AnimalAdvert> {
    boolean deleteByUserId(int id);
}
