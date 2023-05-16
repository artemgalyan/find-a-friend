package by.fpmibsu.findafriend.controller;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.controller.setups.AdvertsSetup;
import by.fpmibsu.findafriend.controller.setups.PlacesSetup;
import by.fpmibsu.findafriend.controller.setups.SheltersSetup;
import by.fpmibsu.findafriend.controller.setups.UsersSetup;
import by.fpmibsu.findafriend.dataaccesslayer.DaoSetup;
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
    private static final List<Setup> setups = List.of(
            new DaoSetup(), new UsersSetup(), new PlacesSetup(),
            new AdvertsSetup(), new SheltersSetup()
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
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPath);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
            return;
        }
        boolean debug = Boolean.parseBoolean(properties.getProperty("debug"));
        var passwordHasher = debug
                ? SimplePasswordHasher.class
                : HashPasswordHasher.class;
        var builder = new ApplicationBuilder();
        setups.forEach(s -> s.applyTo(builder));
        builder.services()
                .addSingleton(Connection.class, () -> connection)
                .addSingleton(PasswordHasher.class, passwordHasher);

        application = builder.build();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        application.send(req, resp);
    }
}
