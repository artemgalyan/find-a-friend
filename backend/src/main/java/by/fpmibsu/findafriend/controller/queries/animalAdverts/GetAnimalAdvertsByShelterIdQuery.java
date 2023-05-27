package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;

import java.util.List;

public class GetAnimalAdvertsByShelterIdQuery extends Request<List<AnimalAdvertModel>> {
    public int id;

    public GetAnimalAdvertsByShelterIdQuery(int id) {
        this.id = id;
    }
}
