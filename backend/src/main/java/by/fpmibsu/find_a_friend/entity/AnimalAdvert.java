package by.fpmibsu.find_a_friend.entity;

import java.util.Date;
import java.util.List;

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

        public char asChar() {
            return c;
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
}
