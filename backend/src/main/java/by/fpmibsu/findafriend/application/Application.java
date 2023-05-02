package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.mediatr.HandlersDataList;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.findafriend.controller.GetPlacesHandler;
import by.fpmibsu.findafriend.controller.GetPlacesRequest;
import by.fpmibsu.findafriend.controller.setups.UsersSetup;
import by.fpmibsu.findafriend.dataaccesslayer.DaoSetup;
import by.fpmibsu.findafriend.services.PasswordHasher;
import by.fpmibsu.findafriend.services.SimplePasswordHasher;

import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

public class Application {
    private static Application application = null;
    private static final List<Setup> setups = List.of(new DaoSetup(), new UsersSetup());

    private static Application setupApplication() {
        var properties = new Properties();
        try {
            properties.load(new FileReader("../conf/config.properties"));
        } catch (IOException exception) {
            System.err.println("Failed to start the app, cannot read config");
            exception.printStackTrace();
            return null;
        }
        var dbPath = (String) properties.getProperty("database_url");
        Connection connection;
        try {
            connection = DriverManager.getConnection(dbPath);
        } catch (SQLException e) {
            System.err.println("Failed to connect to the database.");
            e.printStackTrace();
            return null;
        }
        var builder = new ApplicationBuilder();
        builder.registerHandler(GetPlacesHandler.class, GetPlacesRequest.class);
        setups.forEach(s -> s.applyTo(builder));
        builder.services()
                .addSingleton(Connection.class, () -> connection)
                .addSingleton(PasswordHasher.class, SimplePasswordHasher.class);
        return builder.build();
    }

    public static Application get() {
        if (application == null) {
            application = setupApplication();
        }
        return application;
    }

    private final GlobalServiceProvider globalServiceProvider;
    private final HandlersDataList handlersDataList;
    private final Mediatr mediatr;

    public Application(GlobalServiceProvider globalServiceProvider, HandlersDataList handlers) {
        this.handlersDataList = handlers;
        this.globalServiceProvider = globalServiceProvider;
        this.mediatr = new Mediatr(globalServiceProvider, handlersDataList);
    }

    public <T, R extends Request<? super T>> T send(R request) {
        return new Mediatr(globalServiceProvider, handlersDataList).send(request);
    }
}
