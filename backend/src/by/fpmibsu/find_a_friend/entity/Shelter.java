package by.fpmibsu.find_a_friend.entity;

import java.util.List;

public record Shelter(String name,
                      List<User> administrators,
                      List<AnimalAdvert> animalAdverts,
                      Place place) {}
