package by.fpmibsu.find_a_friend.entity;

public class Place {
    private String country;
    private String region;
    private String city;
    private String district;

    public Place(String country, String region, String city, String district) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.district = district;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }
}
