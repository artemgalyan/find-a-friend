package by.fpmibsu.findafriend.entity;

import java.util.List;
import java.util.Objects;

public class User extends Entity {
    private Contacts contacts;
    private List<AnimalAdvert> animalAdverts;
    private List<Advert> adverts;
    private Role role;
    private String login;
    private String password;

    public enum Role {
        USER,
        MODERATOR,
        ADMINISTRATOR,
        SHELTER_ADMINISTRATOR;

        public int toInt() {
            return switch (this) {
                case USER -> 1;
                case MODERATOR -> 2;
                case ADMINISTRATOR -> 3;
                case SHELTER_ADMINISTRATOR -> 4;
            };
        }

        public static Role fromInt(int value) {
            return switch (value) {
                case 1 -> USER;
                case 2 -> MODERATOR;
                case 3 -> ADMINISTRATOR;
                case 4 -> SHELTER_ADMINISTRATOR;
                default -> throw new IllegalArgumentException("No role for value " + value);
            };
        }

        @Override
        public String toString() {
            return switch (this) {
                case USER -> "User";
                case MODERATOR -> "Moderator";
                case SHELTER_ADMINISTRATOR -> "ShelterAdministrator";
                case ADMINISTRATOR -> "Administrator";
            };
        }
    }

    public User() {
    }

    public User(int id, Contacts contacts, List<AnimalAdvert> animalAdverts, List<Advert> adverts, Role role, String login, String password) {
        this.id = id;
        this.contacts = contacts;
        this.animalAdverts = animalAdverts;
        this.adverts = adverts;
        this.role = role;
        this.login = login;
        this.password = password;
    }

    public Contacts getContacts() {
        return contacts;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public List<AnimalAdvert> getAnimalAdverts() {
        return animalAdverts;
    }

    public void setAnimalAdverts(List<AnimalAdvert> animalAdverts) {
        this.animalAdverts = animalAdverts;
    }

    public List<Advert> getAdverts() {
        return adverts;
    }

    public void setAdverts(List<Advert> adverts) {
        this.adverts = adverts;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(contacts, user.contacts) && role == user.role && Objects.equals(login, user.login) && Objects.equals(password, user.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(contacts, role, login, password);
    }

    @Override
    public String toString() {
        return "User{" +
                "contacts=" + contacts +
                ", role=" + role +
                ", login='" + login + '\'' +
                ", id=" + id +
                '}';
    }
}