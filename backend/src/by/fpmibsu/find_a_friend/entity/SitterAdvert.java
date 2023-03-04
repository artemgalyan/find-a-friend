package by.fpmibsu.find_a_friend.entity;

import java.util.Date;

public record SitterAdvert(int id,
                           String title,
                           String description,
                           Date creationDate,
                           Place place,
                           User owner) {}
