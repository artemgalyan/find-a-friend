package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityProducer {
    public static Advert makeAdvert(ResultSet set) throws SQLException {
        var user = new User();
        user.setId(set.getInt("user_id"));
        return new Advert(
                set.getInt("advert_id"),
                set.getString("title"),
                set.getString("description"),
                set.getDate("creation_date"),
                EntityProducer.makePlace(set),
                user,
                Advert.AdvertType.fromValue(set.getString("advert_type"))
        );
    }

    public static AnimalAdvert makeAnimalAdvert(ResultSet set) throws SQLException {
        var user = new User();
        user.setId(set.getInt("user_id"));
        return new AnimalAdvert(
                set.getInt("animal_advert_id"),
                set.getString("title"),
                set.getString("description"),
                set.getString("name"),
                null,
                user,
                set.getDate("creation_date"),
                makePlace(set),
                set.getDate("birthday"),
                AnimalAdvert.Sex.fromValue(set.getString("sex")),
                booleanFromString(set.getString("is_castrated"))
        );
    }

    public static Photo makePhoto(ResultSet set) throws SQLException {
        Photo photo = new Photo(null);
        photo.setId(set.getInt("photo_id"));
        photo.setData(set.getBytes("data"));
        return photo;
    }


    public static Place makePlace(ResultSet resultSet) throws SQLException {
        return new Place(
                resultSet.getInt("place_id"),
                resultSet.getString("country"),
                resultSet.getString("region"),
                resultSet.getString("city"),
                resultSet.getString("district")
        );
    }

    public static Shelter makeShelter(ResultSet set) throws SQLException {
        return new Shelter(
                set.getInt("id"),
                set.getString("name"),
                null,
                null,
                makePlace(set)
        );
    }

    public static User makeUser(ResultSet set) throws SQLException {
        return new User(
                set.getInt("user_id"),
                new Contacts(
                        set.getString("name"),
                        set.getString("surname"),
                        set.getString("phone_number"),
                        set.getString("email")
                ),
                null,
                null,
                User.Role.USER,
                set.getString("login"),
                set.getString("password")
        );
    }

    private static boolean booleanFromString(String s) {
        return "T".equals(s);
    }
}
