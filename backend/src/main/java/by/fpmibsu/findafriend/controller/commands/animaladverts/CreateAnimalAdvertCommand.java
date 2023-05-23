package by.fpmibsu.findafriend.controller.commands.animaladverts;

import by.fpmibsu.findafriend.application.mediatr.Request;

import java.util.Date;
import java.util.List;

public class CreateAnimalAdvertCommand extends Request<Boolean> {

    public String title;
    public String description;
    public String animalType;
    public int placeId;
    public Date birthdate;
    public String sex;
    public boolean isCastrated;
    public List<byte[]> photos;
}
