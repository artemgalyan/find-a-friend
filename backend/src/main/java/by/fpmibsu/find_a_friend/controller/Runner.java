package by.fpmibsu.find_a_friend.controller;

import by.fpmibsu.find_a_friend.data_access_layer.AdvertDao;
import by.fpmibsu.find_a_friend.data_access_layer.DaoException;
import by.fpmibsu.find_a_friend.data_access_layer.PlaceDao;
import by.fpmibsu.find_a_friend.data_access_layer.UserDao;
import by.fpmibsu.find_a_friend.entity.Advert;
import by.fpmibsu.find_a_friend.entity.Contacts;
import by.fpmibsu.find_a_friend.entity.Place;
import by.fpmibsu.find_a_friend.entity.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Instant;
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
            var usersDao = new UserDao(connection);
            var user = usersDao.getAll().get(0);
            var placeDao = new PlaceDao(connection);
            var place = placeDao.getAll().get(0);
            var advert = new Advert(1, "New Title", "description", Date.from(Instant.now()), place, user, Advert.AdvertType.VOLUNTEER);
            var obj = new AdvertDao(connection).create(advert);
            System.out.println(new ObjectMapper().writeValueAsString(obj));
        } catch (Exception e) {
            System.err.println("Failed to connect to the database.");
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