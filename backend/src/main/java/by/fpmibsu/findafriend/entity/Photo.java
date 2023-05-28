package by.fpmibsu.findafriend.entity;

import java.util.Arrays;
import java.util.Objects;

public class Photo extends Entity {
    private byte[] data;
    private int advertId;

    public Photo() {
    }

    public Photo(byte[] data, int advertId) {
        this.data = data;
        this.advertId = advertId;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public int getAdvertId() {
        return advertId;
    }

    public void setAdvertId(int advertId) {
        this.advertId = advertId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Photo photo = (Photo) o;
        return advertId == photo.advertId && Arrays.equals(data, photo.data);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(advertId);
        result = 31 * result + Arrays.hashCode(data);
        return result;
    }

    @Override
    public String toString() {
        return "Photo{" +
                "advertId=" + advertId +
                ", id=" + id +
                '}';
    }
}
