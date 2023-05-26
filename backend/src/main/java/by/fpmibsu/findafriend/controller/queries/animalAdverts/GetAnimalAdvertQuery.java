package by.fpmibsu.findafriend.controller.queries.animalAdverts;

import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.controller.models.AnimalAdvertModel;

public class GetAnimalAdvertQuery extends Request<AnimalAdvertModel> {
    public int advertId;

    public GetAnimalAdvertQuery(int advertId) {
        this.advertId = advertId;
    }
}
