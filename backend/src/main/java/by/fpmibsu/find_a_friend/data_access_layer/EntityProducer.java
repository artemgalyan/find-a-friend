package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityProducer {
    public static Advert makeAdvert(ResultSet set) {
        return null;
    }

    public static AnimalAdvert makeAnimalAdvert(ResultSet set) throws SQLException {
        return new AnimalAdvert(
                set.getInt("animal_advert_id"),
                set.getString("title"),
                set.getString("description"),
                set.getString("name"),
                null,
                makeUser(set),
                set.getDate("creation_date"),
                makePlace(set),
                set.getDate("birthday"),
                "M".equals(set.getString("sex")) ? AnimalAdvert.Sex.MALE : AnimalAdvert.Sex.FEMALE,
                "T".equals(set.getString("is_castrated"))
        );
    }

    public static Photo makePhoto(ResultSet set) throws SQLException {
        Photo photo = new Photo(null);
        photo.setId(set.getInt("photo_id"));
        photo.setData(set.getBytes("data"));
        return photo;
    }


    public static Place makePlace(ResultSet resultSet) throws SQLException {
        Place place = new Place();
        place.setId(resultSet.getInt("place_id"));
        place.setCountry(resultSet.getString("country"));
        place.setRegion(resultSet.getString("region"));
        place.setCity(resultSet.getString("city"));
        place.setDistrict(resultSet.getString("district"));
        return place;
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
}
