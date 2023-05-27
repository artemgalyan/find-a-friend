package test.fpmibsu.findafriend.application.serviceproviders;

import by.fpmibsu.findafriend.application.serviceproviders.DefaultGlobalServiceProvider;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import by.fpmibsu.findafriend.application.serviceproviders.ServiceAlreadyRegisteredException;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider.*;
import static org.testng.AssertJUnit.*;

public class DefaultServiceProviderTests {
    @DataProvider
    private Object[][] data() {
        return new Object[][]{{ServiceType.TRANSIENT},
                {ServiceType.SCOPED},
                {ServiceType.SINGLETON}};
    }

    @Test(dataProvider = "data")
    void addsAndRetrievesObject(ServiceType type) {
        var object = "1";
        var sp = sp();
        sp.addService(String.class, type, () -> object);
        assertTrue(sp.getRequiredService(String.class) instanceof String);
        assertTrue(sp.hasService(String.class));
    }

    @Test(expectedExceptions = ServiceAlreadyRegisteredException.class)
    void cantRegisterDuplicate() {
        var sp = sp();
        sp.addTransient(Object.class);
        sp.addScoped(Object.class);
    }

    @Test
    void testSingletons() {
        var sp = sp();
        sp.addSingleton(Object.class, Object::new);
        var object = sp.getRequiredService(Object.class);
        assertSame(sp.getRequiredService(Object.class), sp.getRequiredService(Object.class));
    }

    @Test
    void testTransient() {
        var sp = sp();
        sp.addTransient(Object.class, () -> new String());
        assertNotSame(sp.getRequiredService(Object.class), sp.getRequiredService(Object.class));
    }

    @Test
    void testScoped() {
        var sp = sp();
        sp.addScoped(Object.class);
        var scoped = sp.getRequestServiceProvider();
        assertSame(scoped.getRequiredService(Object.class), scoped.getRequiredService(Object.class));
        var scoped2 = sp.getRequestServiceProvider();
        assertNotSame(scoped2.getRequiredService(Object.class), scoped.getRequiredService(Object.class));
    }


    private GlobalServiceProvider sp() {
        return new DefaultGlobalServiceProvider();
    }
}
