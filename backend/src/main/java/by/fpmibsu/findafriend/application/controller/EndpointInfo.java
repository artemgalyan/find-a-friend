package by.fpmibsu.findafriend.application.controller;

import java.lang.reflect.Method;

/**
 * @param controller / класс с информацией об эндпоинте: класс контроллера, метод и адрес эндпоинта
 */
public record EndpointInfo(Class<? extends Controller> controller, Method method, String path, HttpMethod httpMethod) {}
