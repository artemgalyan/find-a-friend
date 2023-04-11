package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Photo;

public interface PhotoDaoInterface extends Dao<Integer, Photo> {
    boolean deleteByPhotoId(int id);
}
