package by.fpmibsu.find_a_friend.entity;

import java.util.Date;
import java.util.List;

public record AnimalAdvert(int id,
                           String title,
                           String description,
                           String animalType,
                           List<Photo> photos,
                           User owner,
                           Date creationDate,
                           Place place,
                           Date birthdate,
                           AnimalAdvert.Sex sex,
                           boolean isCastrated) {
    public enum Sex {
        MALE,
        FEMALE
    }
}
