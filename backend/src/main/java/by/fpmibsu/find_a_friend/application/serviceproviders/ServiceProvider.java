package by.fpmibsu.find_a_friend.application.serviceproviders;

public interface ServiceProvider {
    <T> T getRequiredService(Class<T> clazz);
}
