package by.fpmibsu.findafriend.entity;

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

        public static AdvertType fromValue(String s) {
            return switch (s) {
                case "V" -> VOLUNTEER;
                case "S" -> SITTER;
                default -> throw new IllegalArgumentException("No advert type for " + s);
            };
        }

        public int toInt() {
            return switch (this) {
                case VOLUNTEER -> 1;
                case SITTER -> 0;
                default -> throw new IllegalArgumentException();
            };
        }

        public static AdvertType fromInt(int value) {
            return switch (value) {
                case 0 -> SITTER;
                case 1 -> VOLUNTEER;
                default -> throw new IllegalArgumentException();
            };
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
        return Objects.equals(title, advert.title) && Objects.equals(description, advert.description) && Objects.equals(creationDate, advert.creationDate) && Objects.equals(place, advert.place) && Objects.equals(owner, advert.owner) && advertType == advert.advertType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, creationDate, place, owner, advertType);
    }

    @Override
    public String toString() {
        return "Advert{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", place=" + place +
                ", owner=" + owner +
                ", advertType=" + advertType +
                ", id=" + id +
                '}';
    }
}
