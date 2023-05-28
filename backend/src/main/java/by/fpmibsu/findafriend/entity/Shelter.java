package by.fpmibsu.findafriend.entity;

import java.util.List;
import java.util.Objects;

public class Shelter extends Entity {
    private String name;
    private List<User> administrators;
    private List<AnimalAdvert> animalAdverts;
    private Place place;
    private String address;
    private String website;

    public Shelter() {}

    public Shelter(int id, String name, List<User> administrators, List<AnimalAdvert> animalAdverts, Place place, String address, String website) {
        this.address = address;
        this.website = website;
        this.id = id;
        this.name = name;
        this.administrators = administrators;
        this.animalAdverts = animalAdverts;
        this.place = place;
    }

    public Shelter(String name, List<User> administrators, List<AnimalAdvert> animalAdverts, Place place, String address, String website) {
        this.name = name;
        this.administrators = administrators;
        this.animalAdverts = animalAdverts;
        this.place = place;
        this.address = address;
        this.website = website;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<User> getAdministrators() {
        return administrators;
    }

    public void setAdministrators(List<User> administrators) {
        this.administrators = administrators;
    }

    public List<AnimalAdvert> getAnimalAdverts() {
        return animalAdverts;
    }

    public void setAnimalAdverts(List<AnimalAdvert> animalAdverts) {
        this.animalAdverts = animalAdverts;
    }

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Shelter shelter = (Shelter) o;
        return Objects.equals(name, shelter.name) && Objects.equals(place, shelter.place) && Objects.equals(address, shelter.address) && Objects.equals(website, shelter.website);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, place, address, website);
    }

    @Override
    public String toString() {
        return "Shelter{" +
                "name='" + name + '\'' +
                ", place=" + place +
                ", address='" + address + '\'' +
                ", website='" + website + '\'' +
                ", id=" + id +
                '}';
    }
}
