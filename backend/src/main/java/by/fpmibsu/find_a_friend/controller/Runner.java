package by.fpmibsu.find_a_friend.controller;

import by.fpmibsu.find_a_friend.data_access_layer.DaoException;
import by.fpmibsu.find_a_friend.data_access_layer.PlaceDao;
import by.fpmibsu.find_a_friend.data_access_layer.UserDao;
import by.fpmibsu.find_a_friend.entity.Contacts;
import by.fpmibsu.find_a_friend.entity.Place;
import by.fpmibsu.find_a_friend.entity.User;

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
            var user = new User(1, new Contacts("name", "surname", "123", "mail@mail.com"),
                    null, null, User.Role.USER, "login", "pwd");
            var dao = new UserDao(connection);
            dao.create(user);
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