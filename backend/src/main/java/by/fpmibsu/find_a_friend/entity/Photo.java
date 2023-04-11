package by.fpmibsu.find_a_friend.entity;

public class Photo extends Entity {
    private byte[] data;

    public Photo() {
    }

    public Photo(byte[] data) {
        this.data = data;
    }

    public byte[] getData() {
        return data;
    }

    public void setData(byte[] data) {
        this.data = data;
    }
}
