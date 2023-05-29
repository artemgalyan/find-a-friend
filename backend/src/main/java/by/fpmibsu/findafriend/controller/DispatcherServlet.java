package by.fpmibsu.findafriend.controller;

import by.fpmibsu.findafriend.application.Application;
import by.fpmibsu.findafriend.application.ApplicationBuilder;
import by.fpmibsu.findafriend.application.Setup;
import by.fpmibsu.findafriend.application.authentication.AuthenticationData;
import by.fpmibsu.findafriend.application.requestpipeline.PipelineHandler;
import by.fpmibsu.findafriend.controller.setups.*;
import by.fpmibsu.findafriend.dataaccesslayer.DaoException;
import by.fpmibsu.findafriend.dataaccesslayer.DaoSetup;
import by.fpmibsu.findafriend.dataaccesslayer.pool.ConnectionPool;
import by.fpmibsu.findafriend.dataaccesslayer.validtokens.ValidTokensDao;
import by.fpmibsu.findafriend.services.HashPasswordHasher;
import by.fpmibsu.findafriend.services.PasswordHasher;
import by.fpmibsu.findafriend.services.SimplePasswordHasher;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.List;
import java.util.Properties;

@WebServlet("/*")
public class DispatcherServlet extends HttpServlet {
    private static final Logger rootLogger = LogManager.getLogger();
    private static final Logger logger = LogManager.getLogger(DispatcherServlet.class);
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
            logger.fatal("Failed to start the app, cannot read config. Exception: " + exception);
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
        builder.readKeys("../conf/");
        builder.addPipelineHandler(daoExceptionHandler());
        builder.addAuthentication();
        builder.addPipelineHandler(checkTokenHandler());
        setups.forEach(s -> s.applyTo(builder));
        builder.services()
                .addSingleton(ConnectionPool.class, () -> connectionPool)
                .addSingleton(PasswordHasher.class, passwordHasher)
                .addScoped(Connection.class, (d) -> d.getRequiredService(ConnectionPool.class).getConnection());
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

    private PipelineHandler checkTokenHandler() {
        return (request, response, scopedServiceProvider, endpointInfo, next) -> {
            var authData = scopedServiceProvider.getRequiredService(AuthenticationData.class);
            if (!authData.isValid()) {
                return next.handle(request, response, scopedServiceProvider, endpointInfo, null);
            }
            var tokenDao = scopedServiceProvider.getRequiredService(ValidTokensDao.class);
            int userId = Integer.parseInt(authData.getClaim("id"));
            authData.setValid(tokenDao.isValidToken(authData.getToken(), userId));
            return next.handle(request, response, scopedServiceProvider, endpointInfo, null);
        };
    }

    private PipelineHandler daoExceptionHandler() {
        return ((request, response, scopedServiceProvider, endpointInfo, next) -> {
            try {
                return next.handle(request, response, scopedServiceProvider, endpointInfo, null);
            } catch (Exception e) {
                if (e.getCause() != null && e.getCause() instanceof DaoException de) {
                    logger.error("Caught DaoException. Rollbacking changes. Exception: " + de);
                    var connection = scopedServiceProvider.getRequiredService(Connection.class);
                    connection.rollback();
                }
                throw e;
            }
        });
    }
}
