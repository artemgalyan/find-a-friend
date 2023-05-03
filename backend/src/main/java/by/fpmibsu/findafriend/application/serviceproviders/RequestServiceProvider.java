package by.fpmibsu.findafriend.application.serviceproviders;

import by.fpmibsu.findafriend.application.TestApplication;
import by.fpmibsu.findafriend.services.PasswordHasher;
import by.fpmibsu.findafriend.services.SimplePasswordHasher;

import java.sql.Connection;

public class RequestServiceProvider {
    private static GlobalServiceProvider serviceProvider;

    private static void setup() {
        Connection connection = null;
        serviceProvider = new DefaultGlobalServiceProvider();
        serviceProvider.addSingleton(Connection.class, () -> connection);
        serviceProvider.addSingleton(TestApplication.CountHolder.class);
        serviceProvider.addSingleton(PasswordHasher.class, SimplePasswordHasher::new);
    }

    private static ScopedServiceProvider getServiceProvider() {
        if (serviceProvider == null) {
            setup();
        }
        return serviceProvider.getRequestServiceProvider();
    }
}
