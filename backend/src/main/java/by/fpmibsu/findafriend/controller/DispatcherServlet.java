package by.fpmibsu.findafriend.controller;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.application.controller.HttpMethod;
import by.fpmibsu.findafriend.application.controller.TestController;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.findafriend.controller.controllers.UserController;
import by.fpmibsu.findafriend.controller.setups.UsersSetup;
import by.fpmibsu.findafriend.dataaccesslayer.DaoSetup;
import by.fpmibsu.findafriend.dataaccesslayer.DbUserDao;
import by.fpmibsu.findafriend.services.PasswordHasher;
import by.fpmibsu.findafriend.services.SimplePasswordHasher;

import javax.servlet.ServletException;
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
    private static final List<Setup> setups = List.of(new DaoSetup(), new UsersSetup());

    @Override
    public void init() throws ServletException {
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
        var builder = new ApplicationBuilder();
        setups.forEach(s -> s.applyTo(builder));
        builder.mapController(DemoController.class);
        builder.services()
                .addSingleton(Connection.class, () -> connection)
                .addSingleton(PasswordHasher.class, SimplePasswordHasher.class);
        application = builder.build();
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        application.send(req, resp);
    }
}
