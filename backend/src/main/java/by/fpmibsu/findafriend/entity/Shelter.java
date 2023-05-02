package by.fpmibsu.findafriend.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class Shelter extends Entity {
    private String name;
    private List<User> administrators;
    private List<AnimalAdvert> animalAdverts;
    private Place place;

    public Shelter() {
    }

    public Shelter(int id, String name, List<User> administrators, List<AnimalAdvert> animalAdverts, Place place) {
        this.id = id;
        this.name = name;
        this.administrators = administrators;
        this.animalAdverts = animalAdverts;
        this.place = place;
    }

    public Shelter(String name, List<User> administrators, List<AnimalAdvert> animalAdverts, Place place) {
        this.name = name;
        this.administrators = administrators;
        this.animalAdverts = animalAdverts;
        this.place = place;
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

    @Override
    public String toString() {
        try {
            return new ObjectMapper().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}