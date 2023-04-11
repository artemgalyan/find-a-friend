package by.fpmibsu.find_a_friend.data_access_layer;

import by.fpmibsu.find_a_friend.entity.*;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EntityProducer {
    public static Advert makeAdvert(ResultSet set) {
        return null;
    }

    public static AnimalAdvert makeAnimalAdvert(ResultSet set) {
        return null;
    }

    public static Photo makePhoto(ResultSet set) throws SQLException {
        Photo photo = new Photo();
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

    public static Shelter makeShelter(ResultSet set) {
        return null;
    }

    public static User makeUser(ResultSet set) {
        return null;
    }
}
