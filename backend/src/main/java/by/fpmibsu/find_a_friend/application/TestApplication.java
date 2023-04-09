package by.fpmibsu.find_a_friend.application;

import by.fpmibsu.find_a_friend.application.controllers.Controller;
import by.fpmibsu.find_a_friend.application.controllers.ControllerRoute;
import by.fpmibsu.find_a_friend.application.controllers.Endpoint;
import by.fpmibsu.find_a_friend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.find_a_friend.data_access_layer.PlaceDao;
import by.fpmibsu.find_a_friend.entity.Place;
import by.fpmibsu.find_a_friend.application.serviceproviders.DefaultGlobalServiceProvider;
import by.fpmibsu.find_a_friend.application.mediatr.Mediatr;
import by.fpmibsu.find_a_friend.services.Request;
import by.fpmibsu.find_a_friend.services.RequestHandler;

import java.io.FileReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class TestApplication {

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

    public static class GetPlacesHandler extends RequestHandler<Place[], GetPlacesRequest> {
        private final PlaceDao placeDao;

        public GetPlacesHandler(PlaceDao placeDao) {
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

    @ControllerRoute(route = "api")
    public static class TestController extends Controller {
        @Endpoint(route = "places", method = HttpMethod.GET, requestHandler = GetPlacesHandler.class)
        public Place[] getPlaces(GetPlacesRequest request) {
            return null;
        }

        @Endpoint(route = "count", method = HttpMethod.GET, requestHandler = CountHandler.class)
        public String getCount(CountRequest request) {
            return null;
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

        var builder = new ApplicationBuilder();
        builder.services()
                .addSingleton(Connection.class, () -> connection)
                .addScoped(PlaceDao.class)
                .addSingleton(CountHolder.class);
        builder.mapController(TestController.class);
        builder.setPort(serverPort);
        var application = builder.build();
        application.start();
    }
}
