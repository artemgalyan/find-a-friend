package by.fpmibsu.find_a_friend.controller;

import by.fpmibsu.find_a_friend.data_access_layer.*;
import by.fpmibsu.find_a_friend.entity.*;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
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
            var user = new UserDao(connection).getAll().get(0);
            var place = new PlaceDao(connection).getAll().get(0);
            var advert = new AnimalAdvert(1, "title", "descr", "cat", new ArrayList<>(), user, Date.from(Instant.now()), place, Date.from(Instant.now()), AnimalAdvert.Sex.MALE, false);
            new AnimalAdvertDao(connection).create(advert);
            var photo = new Photo("what a cool text".getBytes(), 2);
            new PhotoDao(connection).create(photo);

        } catch (Exception e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
            return;
        }
        try {
            connection.close();
        } catch (SQLException e) {
            System.err.println("Failed to closed db connection");
            return;
        }
    }
}