package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.AnimalAdvert;

import java.util.Date;

public record AnimalAdvertModel(
        int id,
        int ownerId,
        String title,
        String description,
        String animalType,
        Date creationDate,
        int placeId,
        Date birthDate,
        int sex,
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
                animalAdvert.getPlace().getId(),
                animalAdvert.getBirthdate(),
                animalAdvert.getSex().toInt(),
                animalAdvert.isCastrated(),
                shelterId,
                shelterName
        );
    }
}
