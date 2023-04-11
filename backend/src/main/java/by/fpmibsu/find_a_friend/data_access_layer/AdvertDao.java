package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.Advert;

import java.util.List;

public class AdvertDao implements AdvertDaoInterface {
    private static final String SQL_SELECT_ALL_ADVERTS = """
            SELECT advert_id, advert_type, title, description, creation_date,
            place.place_id, place.country, place.region, place.city, place.district,
            user.user_id, user.name, user.surname, user.email, user.phone_number, user.login, user.password, role.role_id, role.name
            FROM advert
                LEFT JOIN user USING(user_id)
                LEFT JOIN USING(role_id)
                LEFT JOIN USING(place_id)""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT advert_id, advert_type, title, description, creation_date,
            place.place_id, place.country, place.region, place.city, place.district,
            user.user_id, user.name, user.surname, user.email, user.phone_number, user.login, user.password, role.role_id, role.name
            FROM advert
                LEFT JOIN user USING(user_id)
                LEFT JOIN USING(role_id)
                LEFT JOIN USING(place_id)
                    WHERE advert_id=?""";
    private static final String SQL_INSERT_ADVERT = """
            INSERT INTO advert VALUES(?,?,?,?, place_id, user_id)
            WHERE place_id IN (SELECT place_id FROM place
                               WHERE place_id=? AND country=? AND region=? AND city=? AND district=?)
                               AND user_id IN (SELECT user_id FROM user
                                               WHERE user_id=? AND name=? AND surname=? AND email=? AND phone_number=? AND login=? AND password=? AND role_id = ?)""";
    private static final String SQL_DELETE_ADVERT = """
            DELETE
            FROM advert
            WHERE advert_id=?""";
    public static final String SQL_DELETE_BY_ID = """
            DELETE
            FROM advert
            WHERE advert_id=?""";

    @Override
    public List<Advert> getAll() throws DaoException {
        return null;
    }

    @Override
    public Advert getEntityById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(Advert instance) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        return false;
    }

    @Override
    public boolean create(Advert instance) throws DaoException {
        return false;
    }

    @Override
    public Advert update(Advert instance) throws DaoException {
        return null;
    }

    @Override
    public boolean deleteByUserId(int id) {
        return false;
    }
}
