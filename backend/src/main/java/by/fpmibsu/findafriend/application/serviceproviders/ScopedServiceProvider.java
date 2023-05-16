package by.fpmibsu.findafriend.application.serviceproviders;

public interface ScopedServiceProvider extends ServiceProvider {
    <T> ScopedServiceProvider addScoped(Class<T> clazz, T instance);
}
