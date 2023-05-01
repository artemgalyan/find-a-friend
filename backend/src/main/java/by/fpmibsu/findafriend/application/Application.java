package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.mediatr.HandlersDataList;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.findafriend.controller.GetPlacesHandler;
import by.fpmibsu.findafriend.controller.GetPlacesRequest;
import by.fpmibsu.findafriend.dataaccesslayer.DaoRegisterer;
import by.fpmibsu.findafriend.entity.Place;
import by.fpmibsu.findafriend.services.PasswordHasher;
import by.fpmibsu.findafriend.services.SimplePasswordHasher;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Application {
    private static Application application = null;

    private static Application setupApplication() {
        var properties = new Properties();
        try {
            properties.load(new FileReader("D:\\Programming\\4th-semester\\programming-technologies\\find-a-friend\\backend\\config\\config.properties"));
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
        DaoRegisterer.register(builder.getServiceProvider());
        builder.registerHandler(GetPlacesHandler.class, Place[].class, GetPlacesRequest.class);
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
