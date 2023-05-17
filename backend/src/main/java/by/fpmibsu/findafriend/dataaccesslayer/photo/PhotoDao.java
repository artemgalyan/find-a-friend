package by.fpmibsu.findafriend.dataaccesslayer.photo;

import by.fpmibsu.findafriend.dataaccesslayer.Dao;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.entity.Photo;

import java.util.List;

public interface PhotoDao extends Dao<Integer, Photo> {
    boolean deleteByAnimalAdvertId(int id) throws DaoException;

    List<Photo> getAdvertPhotos(int advertId) throws DaoException;

    void create(List<Photo> photos) throws DaoException;
}
