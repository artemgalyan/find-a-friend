package by.fpmibsu.find_a_friend.entity;

import java.util.Date;
import java.util.Objects;

public class Advert extends Entity {
    public AdvertType getAdvertType() {
        return advertType;
    }

    public void setAdvertType(AdvertType advertType) {
        this.advertType = advertType;
    }

    public enum AdvertType {
        VOLUNTEER('V'),
        SITTER('S');

        private final char c;
        AdvertType(char c) {
            this.c = c;
        }

        public char getValue() {
            return c;
        }
    }
    private String title;
    private String description;
    private Date creationDate;
    private Place place;
    private User owner;
    private AdvertType advertType;

    public Advert(int id, String title, String description, Date creationDate, Place place, User owner, AdvertType advertType) {
        this.advertType = advertType;
        this.id = id;
        this.title = title;
        this.description = description;
        this.creationDate = creationDate;
        this.place = place;
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Advert advert = (Advert) o;
        return id == advert.id && creationDate.equals(advert.creationDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, creationDate);
    }
}
