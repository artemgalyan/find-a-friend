package by.fpmibsu.find_a_friend.application;

import by.fpmibsu.find_a_friend.data_access_layer.PlaceDao;
import by.fpmibsu.find_a_friend.entity.Place;
import by.fpmibsu.find_a_friend.entity.User;
import by.fpmibsu.find_a_friend.services.DIContainer;
import by.fpmibsu.find_a_friend.utils.Mediatr;
import by.fpmibsu.find_a_friend.utils.Request;
import by.fpmibsu.find_a_friend.utils.RequestHandler;

import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class TestApplication {
    public static class EmptyRequest extends Request<String> {
    }

    public static class EmptyRequestHandler extends RequestHandler<String, EmptyRequest> {
        @Override
        public String handle(EmptyRequest request) {
            return "Hello, world!";
        }
    }

    public static class CountRequest extends Request<String> {
    }

    public static class CountHolder {
        private int count = 0;

        public void increment() {
            ++count;
        }

        public int getCount() {
            return count;
        }
    }

    public static class CountHandler extends RequestHandler<String, CountRequest> {
        private final CountHolder countHolder;

        public CountHandler(CountHolder countHolder) {
            this.countHolder = countHolder;
        }

        @Override
        public String handle(CountRequest request) {
            String response = "This page was visited " + countHolder.getCount() + " times!";
            countHolder.increment();
            return response;
        }
    }

    public static class GetPlacesRequest extends Request<Place[]> {
    }

    public static class GetUsersHandler extends RequestHandler<Place[], GetPlacesRequest> {
        private final PlaceDao placeDao;

        public GetUsersHandler(CountHolder countHolder, PlaceDao placeDao) {
            this.placeDao = placeDao;
        }

        @Override
        public Place[] handle(GetPlacesRequest request) {
            try {
                return placeDao.getAll().toArray(Place[]::new);
            } catch (Exception e) {
                return null;
            }
        }
    }

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
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
            return;
        }
        int serverPort = 8080;
        var diContainer = new DIContainer();
        diContainer.addService(CountHolder.class, DIContainer.ServiceType.SINGLETON, CountHolder::new);
        diContainer.addService(Connection.class, DIContainer.ServiceType.SINGLETON, () -> connection);
        diContainer.addService(PlaceDao.class, DIContainer.ServiceType.SCOPED, d -> new PlaceDao(d.getRequiredService(Connection.class)));
        var mediatr = new Mediatr(diContainer);
        mediatr.registerHandler(EmptyRequestHandler.class, String.class, EmptyRequest.class);
        mediatr.registerHandler(CountHandler.class, String.class, CountRequest.class);
        mediatr.registerHandler(GetUsersHandler.class, Place[].class, GetPlacesRequest.class);
        List<Endpoint<?, ?>> endpoints = List.of(new Endpoint<>("/", EmptyRequest.class, String.class, "GET"),
                new Endpoint<>("/count", CountRequest.class, String.class, "GET"),
                new Endpoint<>("/places", GetPlacesRequest.class, Place[].class, "GET"));
        var application = new Application(new InetSocketAddress(serverPort), 0, endpoints,
                diContainer,
                List.of(new EndpointsSender(endpoints, mediatr)));
        application.start();
    }
}
