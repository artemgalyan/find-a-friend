package by.fpmibsu.find_a_friend.controller;

import by.fpmibsu.find_a_friend.data_access_layer.DaoException;
import by.fpmibsu.find_a_friend.data_access_layer.PlaceDao;
import by.fpmibsu.find_a_friend.entity.Place;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Runner {
    public static void main(String[] args) {
        var properties = new Properties();
        try {
            properties.load(new FileReader("config/config.properties"));
        } catch (IOException exception) {
            System.err.println("Failed to start the app, cannot read config");
            exception.printStackTrace();
            return;
        }
        var dbPath = (String) properties.getProperty("database_url");
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPath);
            PlaceDao placeDao = new PlaceDao(connection);
            List<Place> places = placeDao.getAll();
            for (var place : places) {
                System.out.println(place);
            }
            Place place = placeDao.getEntityById(2);
            System.out.println(place);

            Place new_place = new Place(4, "Беларусь", "Минский район", "Минск", "Московский");
            placeDao.create(new_place);
            placeDao.delete(3);
            placeDao.delete(1);


        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            return;
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Failed to closed db connection");
            return;
        }
    }
}