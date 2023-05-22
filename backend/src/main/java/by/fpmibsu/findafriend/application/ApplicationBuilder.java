package by.fpmibsu.findafriend.application;

import by.fpmibsu.findafriend.application.controller.Controller;
import by.fpmibsu.findafriend.application.controller.ControllerMapper;
import by.fpmibsu.findafriend.application.controller.EndpointInfo;
import by.fpmibsu.findafriend.application.mediatr.HandlersDataList;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;
import by.fpmibsu.findafriend.application.mediatr.Request;
import by.fpmibsu.findafriend.application.mediatr.RequestHandler;
import by.fpmibsu.findafriend.application.serviceproviders.DefaultGlobalServiceProvider;
import by.fpmibsu.findafriend.application.serviceproviders.GlobalServiceProvider;
import org.jose4j.jwk.JsonWebKey;
import org.jose4j.jwk.RsaJsonWebKey;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.util.ArrayList;
import java.util.List;

public class ApplicationBuilder {
    private final GlobalServiceProvider serviceProvider = new DefaultGlobalServiceProvider();
    private final HandlersDataList handlersDataList = new HandlersDataList();
    private final List<EndpointInfo> endpointInfos = new ArrayList<>();
    private Application.Keys keys;

    public ApplicationBuilder() {
        serviceProvider.addService(HandlersDataList.class, GlobalServiceProvider.ServiceType.SINGLETON, () -> handlersDataList);
        serviceProvider.addScoped(Mediatr.class);
    }

    public Application build() {
        return new Application(serviceProvider, endpointInfos, keys);
    }

    public ApplicationBuilder readKeys(String path) {
        try {
            var input = new ObjectInputStream(
                    new FileInputStream(path + "public")
            );
            var publicKey = (RSAPublicKey) input.readObject();
            input.close();
            input = new ObjectInputStream(
                    new FileInputStream(path + "private")
            );
            var privateKey = (RSAPrivateKey) input.readObject();
            input.close();
            keys = new Application.Keys(publicKey, privateKey);
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return this;
    }

    public GlobalServiceProvider services() {
        return serviceProvider;
    }

    public <T, R extends Request<? super T>> ApplicationBuilder registerHandler(Class<? extends RequestHandler<T, R>> clazz, Class<R> requestType) {
        handlersDataList.registerHandler(clazz, requestType);
        return this;
    }

    public GlobalServiceProvider getServiceProvider() {
        return serviceProvider;
    }

    public ApplicationBuilder mapController(Class<? extends Controller> controller) {
        List<EndpointInfo> data = ControllerMapper.mapController(controller);
        endpointInfos.addAll(data);
        return this;
    }
}
