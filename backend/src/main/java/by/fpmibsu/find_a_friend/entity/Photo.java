package by.fpmibsu.find_a_friend.entity;

public class Photo extends Entity {
    private byte[] data;
    private int advertId;

    public Photo() {}

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
}
