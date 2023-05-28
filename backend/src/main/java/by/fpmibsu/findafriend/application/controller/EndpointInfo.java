package by.fpmibsu.findafriend.application.controller;

/**
 * @param controller / класс с информацией об эндпоинте: класс контроллера, метод и адрес эндпоинта
 */
public record EndpointInfo(Class<? extends Controller> controller,
                           java.lang.reflect.Method method, String path, HttpMethod httpMethod) {
    public boolean requiresAuthentication() {
        return method.isAnnotationPresent(RequireAuthentication.class);
    }
}
