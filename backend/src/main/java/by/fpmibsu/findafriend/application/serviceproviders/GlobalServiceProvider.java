package by.fpmibsu.findafriend.application.serviceproviders;

import by.fpmibsu.findafriend.application.utils.ObjectConstructor;

import java.util.function.Function;
import java.util.function.Supplier;

public interface GlobalServiceProvider extends ServiceProvider {
    enum ServiceType {
        SCOPED,
        SINGLETON,
        TRANSIENT
    }

    <T> GlobalServiceProvider addService(Class<T> clazz, ServiceType type, Function<ServiceProvider, ? extends T> supplier);

    ScopedServiceProvider getRequestServiceProvider();

    default <T> GlobalServiceProvider addService(Class<T> registeredClazz, ServiceType type, Class<? extends T> realClazz) {
        return addService(registeredClazz, type, () -> ObjectConstructor.createInstance(realClazz, this));
    }

    default <T> GlobalServiceProvider addService(Class<T> clazz, ServiceType type, Supplier<? extends T> supplier) {
        return addService(clazz, type, (d) -> supplier.get());
    }

    default <T> GlobalServiceProvider addService(Class<T> clazz, ServiceType type) {
        return addService(clazz, type, d -> ObjectConstructor.createInstance(clazz, this));
    }

    default <T> GlobalServiceProvider addScoped(Class<T> clazz, Function<ServiceProvider, ? extends T> supplier) {
        return addService(clazz, ServiceType.SCOPED, supplier);
    }

    default <T> GlobalServiceProvider addScoped(Class<T> clazz, Supplier<? extends T> supplier) {
        return addScoped(clazz, (d) -> supplier.get());
    }

    default <T> GlobalServiceProvider addScoped(Class<T> clazz) {
        return addScoped(clazz, (d) -> ObjectConstructor.createInstance(clazz, this));
    }

    default <T> GlobalServiceProvider addScoped(Class<T> registeredClass, Class<? extends T> actualClass) {
        return addService(registeredClass, ServiceType.SCOPED, actualClass);
    }


    default <T> GlobalServiceProvider addSingleton(Class<T> clazz, Function<ServiceProvider, ? extends T> supplier) {
        return addService(clazz, ServiceType.SINGLETON, supplier);
    }

    default <T> GlobalServiceProvider addSingleton(Class<T> clazz, Supplier<? extends T> supplier) {
        return addSingleton(clazz, (d) -> supplier.get());
    }

    default <T> GlobalServiceProvider addSingleton(Class<T> clazz) {
        return addSingleton(clazz, (d) -> ObjectConstructor.createInstance(clazz, this));
    }

    default <T> GlobalServiceProvider addSingleton(Class<T> registeredClass, Class<? extends T> actualClass) {
        return addService(registeredClass, ServiceType.SINGLETON, actualClass);
    }


    default <T> GlobalServiceProvider addTransient(Class<T> clazz, Function<ServiceProvider, ? extends T> supplier) {
        return addService(clazz, ServiceType.TRANSIENT, supplier);
    }

    default <T> GlobalServiceProvider addTransient(Class<T> clazz, Supplier<? extends T> supplier) {
        return addTransient(clazz, (d) -> supplier.get());
    }

    default <T> GlobalServiceProvider addTransient(Class<T> clazz) {
        return addTransient(clazz, (d) -> ObjectConstructor.createInstance(clazz, this));
    }

    default <T> GlobalServiceProvider addTransient(Class<T> registeredClass, Class<? extends T> actualClass) {
        return addService(registeredClass, ServiceType.TRANSIENT, actualClass);
    }
}
