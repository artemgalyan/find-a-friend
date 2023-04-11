package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Place;
import by.fpmibsu.find_a_friend.entity.Entity;

public interface PlaceDaoInterface extends Dao<Integer, Place> {
    boolean deleteByPlaceId(int id);
}
