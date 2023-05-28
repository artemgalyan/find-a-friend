package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.Photo;

import java.util.Base64;

public record PhotoModel(String base64content) implements Model<Photo> {
    public static PhotoModel of(Photo p) {
        return new PhotoModel(new String(Base64.getDecoder().decode(p.getData())));
    }
}
