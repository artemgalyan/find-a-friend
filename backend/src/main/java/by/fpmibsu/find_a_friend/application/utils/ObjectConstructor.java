package by.fpmibsu.find_a_friend.application.utils;

import by.fpmibsu.find_a_friend.application.serviceproviders.ServiceProvider;

import java.lang.reflect.Constructor;

public class ObjectConstructor {
    public static <T> T createInstance(Class<T> clazz, ServiceProvider serviceProvider) {
        Constructor<T> ctor = (Constructor<T>) clazz.getDeclaredConstructors()[0];
        var numberOfParameters = ctor.getParameters().length;

        var params = new Object[numberOfParameters];
        for (int i = 0; i < numberOfParameters; ++i) {
            var service = serviceProvider.getRequiredService(ctor.getParameters()[i].getType());
            params[i] = service;
        }
        try {
            return ctor.newInstance(params);
        } catch (Exception e) {
            return null;
        }
    }
}
