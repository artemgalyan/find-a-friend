package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.AnimalAdvert;
import by.fpmibsu.findafriend.entity.Place;

import java.util.Date;

public record AnimalAdvertModel(
        int advertId,
        int userId,
        String title,
        String description,
        String animalType,
        Date creationDate,
        Place place,
        Date birthDate,
        char sex,
        boolean isCastrated,
        int shelterId,
        String shelterName) implements Model<AnimalAdvert> {

    public static AnimalAdvertModel of(AnimalAdvert animalAdvert, int shelterId, String shelterName) {
        return new AnimalAdvertModel(
                animalAdvert.getId(),
                animalAdvert.getOwner().getId(),
                animalAdvert.getTitle(),
                animalAdvert.getDescription(),
                animalAdvert.getAnimalType(),
                animalAdvert.getCreationDate(),
                animalAdvert.getPlace(),
                animalAdvert.getBirthdate(),
                animalAdvert.getSex().getValue(),
                animalAdvert.isCastrated(),
                shelterId,
                shelterName
        );
    }
}
