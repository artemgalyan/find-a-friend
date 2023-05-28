package by.fpmibsu.findafriend.entity;

import java.util.Objects;

public class Place extends Entity {
    private String country;
    private String region;
    private String city;
    private String district;

    public Place(int id, String country, String region, String city, String district) {
        this.id = id;
        this.country = country;
        this.region = region;
        this.city = city;
        this.district = district;
    }

    public Place(String country, String region, String city, String district) {
        this.country = country;
        this.region = region;
        this.city = city;
        this.district = district;
    }

    public Place() {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Place place = (Place) o;
        return Objects.equals(country, place.country) && Objects.equals(region, place.region) && Objects.equals(city, place.city) && Objects.equals(district, place.district);
    }

    @Override
    public int hashCode() {
        return Objects.hash(country, region, city, district);
    }

    @Override
    public String toString() {
        return "Place{" +
                "country='" + country + '\'' +
                ", region='" + region + '\'' +
                ", city='" + city + '\'' +
                ", district='" + district + '\'' +
                ", id=" + id +
                '}';
    }
}
