package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Photo;

import java.util.List;

public interface PhotoDao extends Dao<Integer, Photo> {
    boolean deleteByAnimalAdvertId(int id) throws DaoException;

    List<Photo> getAdvertPhotos(int advertId) throws DaoException;

    void create(List<Photo> photos) throws DaoException;
}
