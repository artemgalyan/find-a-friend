package by.fpmibsu.find_a_friend.controller;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ConnectionBuilder;
import java.sql.DriverManager;
import java.sql.SQLException;
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
        Connection connection = null;
        try {
            connection = DriverManager.getConnection(dbPath);
        } catch (SQLException e) {
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