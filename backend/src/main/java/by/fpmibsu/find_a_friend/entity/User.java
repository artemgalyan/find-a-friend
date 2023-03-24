package by.fpmibsu.find_a_friend.entity;

import java.util.List;

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
        SHELTER_ADMINISTRATOR
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
}