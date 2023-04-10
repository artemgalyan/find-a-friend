package by.fpmibsu.find_a_friend.application;

import by.fpmibsu.find_a_friend.application.controllers.Controller;
import by.fpmibsu.find_a_friend.application.controllers.ControllerRoute;
import by.fpmibsu.find_a_friend.application.controllers.Endpoint;
import by.fpmibsu.find_a_friend.data_access_layer.DaoException;
import by.fpmibsu.find_a_friend.data_access_layer.PlaceDao;
import by.fpmibsu.find_a_friend.entity.Place;
import by.fpmibsu.find_a_friend.services.HttpExchangeAccessor;
import by.fpmibsu.find_a_friend.application.mediatr.Request;
import by.fpmibsu.find_a_friend.application.mediatr.RequestHandler;
import by.fpmibsu.find_a_friend.services.PasswordHasher;
import by.fpmibsu.find_a_friend.services.SimplePasswordHasher;
import com.sun.net.httpserver.HttpExchange;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
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

    public static class GetPlacesRequestHandler extends RequestHandler<Place[], GetPlacesRequest> {
        private final PlaceDao placeDao;

        public GetPlacesRequestHandler(PlaceDao placeDao) {
            this.placeDao = placeDao;
        }

        @Override
        public Place[] handle(GetPlacesRequest request) {
            try {
                return placeDao.getAll().toArray(Place[]::new);
            } catch (DaoException e) {
                e.printStackTrace();
                return null;
            }
        }
    }

    public static class HttpExchangeAccessExampleRequest extends Request<String> {
    }

    public static class ExchangeHandler extends RequestHandler<String, HttpExchangeAccessExampleRequest> {
        private final HttpExchange exchange;

        public ExchangeHandler(HttpExchange exchange) {
            this.exchange = exchange;
        }

        @Override
        public String handle(HttpExchangeAccessExampleRequest request) {
            return exchange.getProtocol();
        }
    }

    @ControllerRoute(route = "api")
    public interface TestController extends Controller {
        @Endpoint(route = "places", method = HttpMethod.GET, requestHandler = GetPlacesRequestHandler.class)
        public Place[] getPlaces(GetPlacesRequest request);
        
        @Endpoint(route = "count", method = HttpMethod.GET, requestHandler = CountHandler.class)
        public String getCount(CountRequest request);

        @Endpoint(route = "http", method = HttpMethod.GET, requestHandler = ExchangeHandler.class)
        public String httpExample(HttpExchangeAccessExampleRequest request);
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
        int serverPort;
        try {
            serverPort = Integer.parseInt(properties.getProperty("port"));
        } catch (Exception e) {
            serverPort = 8080;
        }

        var builder = new ApplicationBuilder();
        builder.services()
                .addSingleton(Connection.class, () -> connection)
                .addScoped(PlaceDao.class)
                .addSingleton(CountHolder.class)
                .addSingleton(PasswordHasher.class, SimplePasswordHasher::new);
        builder.mapController(TestController.class);
        builder.setPort(serverPort);
        builder.addPipeLineHandler(new HttpExchangeAccessor());
        var application = builder.build();
        application.start();
    }
}