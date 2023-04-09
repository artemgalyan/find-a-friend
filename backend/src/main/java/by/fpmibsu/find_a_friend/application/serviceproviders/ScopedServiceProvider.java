package by.fpmibsu.find_a_friend.application.serviceproviders;

public interface ScopedServiceProvider extends ServiceProvider {
    <T> ScopedServiceProvider addScoped(Class<T> clazz, T instance);
}
