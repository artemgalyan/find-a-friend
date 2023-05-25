package by.fpmibsu.findafriend.controller;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.controllers.ExampleController;
import by.fpmibsu.findafriend.controller.setups.*;
import by.fpmibsu.findafriend.dataaccesslayer.DaoSetup;
import by.fpmibsu.findafriend.dataaccesslayer.pool.ConnectionPool;
import by.fpmibsu.findafriend.services.HashPasswordHasher;
import by.fpmibsu.findafriend.services.PasswordHasher;
import by.fpmibsu.findafriend.services.SimplePasswordHasher;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {
    private Application application;
    private ConnectionPool connectionPool;
    private static final List<Setup> setups = List.of(
            new DaoSetup(), new UsersSetup(), new PlacesSetup(),
            new AdvertsSetup(), new SheltersSetup(), new AuthSetup(),
            new AnimalAdvertSetup(), new UserShelterSetup(), new PhotoSetup()
    );

    @Override
    public void init() {
        var properties = new Properties();
        try {
            properties.load(new FileReader("../conf/config.properties"));
        } catch (IOException exception) {
            System.err.println("Failed to start the app, cannot read config");
            exception.printStackTrace();
            return;
        }
        var dbPath = (String) properties.getProperty("database_url");
        connectionPool = new ConnectionPool(dbPath);
        int startSize = Integer.parseInt(properties.getProperty("start_size", "1"));
        int maxSize = Integer.parseInt(properties.getProperty("max_size", "50"));
        int checkout = Integer.parseInt(properties.getProperty("checkout", "0"));
        connectionPool.init(startSize, maxSize, checkout);
        boolean debug = Boolean.parseBoolean(properties.getProperty("debug"));
        var passwordHasher = debug
                ? SimplePasswordHasher.class
                : HashPasswordHasher.class;
        var builder = new ApplicationBuilder();
        builder.mapController(ExampleController.class);
        builder.readKeys("../conf/");
        setups.forEach(s -> s.applyTo(builder));
        builder.services()
                .addSingleton(ConnectionPool.class, () -> connectionPool)
                .addSingleton(PasswordHasher.class, passwordHasher)
                .addTransient(Connection.class, (d) -> d.getRequiredService(ConnectionPool.class).getConnection());
        application = builder.build();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        application.send(req, resp);
    }

    @Override
    public void destroy() {
        connectionPool.destroy();
    }
}
