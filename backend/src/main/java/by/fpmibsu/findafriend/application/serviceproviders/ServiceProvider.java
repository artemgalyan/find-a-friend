package by.fpmibsu.findafriend.application.serviceproviders;

public interface ServiceProvider {
    <T> T getRequiredService(Class<T> clazz);
    <T> boolean hasService(Class<T> clazz);
}
