package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Photo;

import java.util.List;

public interface PhotoDaoInterface extends Dao<Integer, Photo> {
    boolean deleteByAnimalAdvertId(int id) throws DaoException;
    List<Photo> getAdvertPhotos(int advertId) throws DaoException;
}
