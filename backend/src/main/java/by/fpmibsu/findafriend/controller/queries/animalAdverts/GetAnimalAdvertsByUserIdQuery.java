package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;

import java.util.List;

public class GetAnimalAdvertsByUserIdQuery extends Request<List<AnimalAdvertModel>> {
    public int userId;

    public GetAnimalAdvertsByUserIdQuery(int userId) {
        this.userId = userId;
    }
}
