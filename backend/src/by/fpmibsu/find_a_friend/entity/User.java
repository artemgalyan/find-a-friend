package by.fpmibsu.find_a_friend.entity;

import java.util.List;

public record User(int id,
                   Contacts contacts,
                   List<AnimalAdvert> animalAdverts,
                   List<VolunteerAdvert> volunteerAdverts,
                   List<SitterAdvert> sitterAdverts,
                   String role) {}