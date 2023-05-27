package by.fpmibsu.findafriend.application.serviceproviders;

public interface ScopedServiceProvider extends ServiceProvider, AutoCloseable {
    <T> ScopedServiceProvider addScoped(Class<T> clazz, T instance);
}
