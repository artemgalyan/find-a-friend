package by.fpmibsu.findafriend.controller.models;

import by.fpmibsu.findafriend.entity.Photo;

import java.util.Base64;

public class PhotoModel {
    public String base64content;

    public PhotoModel(String base64content) {
        this.base64content = base64content;
    }

    public static PhotoModel of(Photo p) {
        return new PhotoModel(new String(Base64.getDecoder().decode(p.getData())));
    }
}
