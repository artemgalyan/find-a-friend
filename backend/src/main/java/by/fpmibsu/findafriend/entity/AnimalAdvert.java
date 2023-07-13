package by.fpmibsu.findafriend.entity;

import java.util.Date;
import java.util.List;
import java.util.Objects;

public class AnimalAdvert extends Entity {
    private String title;
    private String description;
    private String animalType;
    private List<Photo> photos;
    private User owner;
    private Date creationDate;
    private Place place;
    private Date birthdate;
    private Sex sex;
    private boolean isCastrated;

    public enum Sex {
        MALE('M'),
        FEMALE('F');
        private final char c;

        Sex(char c) {
            this.c = c;
        }

        public char getValue() {
            return c;
        }
        public static Sex fromValue(String value) {
            return switch (value) {
                case "M" -> MALE;
                case "F" -> FEMALE;
                default -> throw new IllegalArgumentException("No sex for string " + value);
            };
        }

        public int toInt() {
            return switch (this) {
                case MALE -> 0;
                case FEMALE -> 1;
            };
        }

        public static Sex fromInt(int value) {
            return switch (value) {
                case 0 -> MALE;
                case 1 -> FEMALE;
                default -> throw new IllegalArgumentException();
            };
        }
    }

    public AnimalAdvert(int id, String title, String description, String animalType,
                        List<Photo> photos, User owner, Date creationDate, Place place,
                        Date birthdate, Sex sex, boolean isCastrated) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.animalType = animalType;
        this.photos = photos;
        this.owner = owner;
        this.creationDate = creationDate;
        this.place = place;
        this.birthdate = birthdate;
        this.sex = sex;
        this.isCastrated = isCastrated;
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

    public String getAnimalType() {
        return animalType;
    }

    public void setAnimalType(String animalType) {
        this.animalType = animalType;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(List<Photo> photos) {
        this.photos = photos;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
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

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public boolean isCastrated() {
        return isCastrated;
    }

    public void setCastrated(boolean castrated) {
        isCastrated = castrated;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalAdvert that = (AnimalAdvert) o;
        return isCastrated == that.isCastrated && Objects.equals(title, that.title) && Objects.equals(description, that.description) && Objects.equals(animalType, that.animalType) && Objects.equals(owner, that.owner) && Objects.equals(creationDate, that.creationDate) && Objects.equals(place, that.place) && Objects.equals(birthdate, that.birthdate) && sex == that.sex;
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, animalType, owner, creationDate, place, birthdate, sex, isCastrated);
    }

    @Override
    public String toString() {
        return "AnimalAdvert{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", animalType='" + animalType + '\'' +
                ", owner=" + owner +
                ", creationDate=" + creationDate +
                ", place=" + place +
                ", birthdate=" + birthdate +
                ", sex=" + sex +
                ", isCastrated=" + isCastrated +
                ", id=" + id +
                '}';
    }
}
