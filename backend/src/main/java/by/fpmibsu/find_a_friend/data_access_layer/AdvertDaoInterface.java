package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Advert;
public interface AdvertDao implements DaoInterface<Integer, Advert> {

    public List<Advert> getAll();

    public Advert getEntityById(Integer id);

    public boolean delete(Advert instance);

    public boolean delete(Integer value);

    public boolean create(Advert instance);
    public Advert update(Advert instance);
}