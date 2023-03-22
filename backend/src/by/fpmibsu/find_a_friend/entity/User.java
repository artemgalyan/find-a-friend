package by.fpmibsu.find_a_friend.entity;

import java.util.List;

public record User(int id,
                   Contacts contacts,
                   List<AnimalAdvert> animalAdverts,
                   List<Advert> adverts,
                   String role,
                   String login,
                   String password) {
    public enum Role {
        USER,
        MODERATOR,
        ADMINISTRATOR,
        SHELTER_ADMINISTRATOR
    }
}