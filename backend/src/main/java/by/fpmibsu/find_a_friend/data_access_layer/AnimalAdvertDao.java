package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.AnimalAdvert;

import java.util.List;

public class AnimalAdvertDao implements AnimalAdvertDaoInterface {
    private static final String SQL_SELECT_ALL_ANIMALADVERTS = """
            SELECT animal_advert_id, title, description, creation_date, birthday, sex, castrated
            AnimalType.animal_type_id, AnimalType.name,
            user.user_id, user.name, user.surname, user.email, user.phone_number, user.login, user.password, role.role_id, role.name,
            place.place_id, place.country, place.region, place.city, place.district
            FROM AnimalAdvert
                LEFT JOIN user USING(user_id)
                LEFT JOIN USING(role_id)
                LEFT JOIN USING(place_id)
                LEFT JOIN USING(animal_type_id)""";
    private static final String SQL_SELECT_BY_ID = """
            SELECT animal_advert_id, title, description, creation_date, birthday, sex, castrated,
             AnimalType.animal_type_id, AnimalType.name,
             user.user_id, user.name, user.surname, user.email, user.phone_number, user.login, user.password, role.role_id, role.name,
            place.place_id, place.country, place.region, place.city, place.district
            FROM AnimalAdvert
                LEFT JOIN user USING(user_id)
                LEFT JOIN USING(role_id)
                LEFT JOIN USING(place_id)
                LEFT JOIN USING(animal_type_id)
                WHERE animal_advert_id=?""";
    private static final String SQL_INSERT_ANIMALADVERT = """
            INSERT INTO advert VALUES(?,?,?,?,?,?, animal_type_id, user_id, place_id)
            WHERE place_id IN (SELECT place_id FROM place WHERE place_id=? AND country=? AND region=? AND city=? AND district=?)
             AND user_id IN (SELECT user_id FROM user WHERE user_id=? AND name=? AND surname=? AND email=? AND phone_number=? AND login=? AND password=? AND role_id = ?)
             AND animal_type_id=?)""";
    private static final String SQL_DELETE_ANIMALADVERT = """
            DELETE
            FROM AnimalAdvert
            WHERE animal_advert_id=?""";
    public static final String SQL_DELETE_BY_ID = """
            DELETE
            FROM AnimalAdvert
            WHERE animal_advert_id=?""";

    @Override
    public List<AnimalAdvert> getAll() throws DaoException {
        return null;
    }

    @Override
    public AnimalAdvert getEntityById(Integer id) throws DaoException {
        return null;
    }

    @Override
    public boolean delete(AnimalAdvert instance) throws DaoException {
        return false;
    }

    @Override
    public boolean delete(Integer value) throws DaoException {
        return false;
    }

    @Override
    public boolean create(AnimalAdvert instance) throws DaoException {
        return false;
    }

    @Override
    public AnimalAdvert update(AnimalAdvert instance) throws DaoException {
        return null;
    }

    @Override
    public boolean deleteByUserId(int id) {
        return false;
    }
}
