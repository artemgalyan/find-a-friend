package by.fpmibsu.find_a_friend.services;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class DIContainer {
    public enum ServiceType {
        SCOPED,
        SINGLETON
    }

    private static class ClassEntry<T> {
        public Class<T> clazz;
        public Function<DIContainer, ? extends T> producer;
        public ServiceType type;

        public ClassEntry(Class<T> clazz, Function<DIContainer, ? extends T> producer, ServiceType type) {
            this.clazz = clazz;
            this.producer = producer;
            this.type = type;
        }
    }

    private final List<ClassEntry<?>> entries = new ArrayList<>();
    private final List<Object> singletons = new ArrayList<>();

    public <T> T createObject(Class<T> clazz) {
        Constructor<T> ctor = (Constructor<T>) clazz.getDeclaredConstructors()[0];
        var numberOfParameters = ctor.getParameters().length;

        var params = new Object[numberOfParameters];
        for (int i = 0; i < numberOfParameters; ++i) {
            var service = getRequiredService(ctor.getParameters()[i].getType());
            params[i] = service;
        }
        try {
            return ctor.newInstance(params);
        } catch (Exception e) {
            return null;
        }
    }

    public <T> T getRequiredService(Class<T> clazz) {
        var singleton = singletons.stream().filter(o -> clazz.equals(o.getClass())).findFirst();
        if (singleton.isPresent()) {
            return (T) singleton;
        }
        var item = entries.stream().filter(o -> clazz.equals(o.clazz)).findFirst();
        if (item.isEmpty()) {
            throw new ServiceNotFoundException();
        }

        if (item.get().type == ServiceType.SINGLETON) {
            var object = item.get().producer.apply(this);
            singletons.add(object);
            return (T) object;
        }

        return (T) item.get().producer.apply(this);
    }

    public <T> DIContainer addService(Class<T> clazz, ServiceType type, Function<DIContainer, ? extends T> producer) {
        if (entries.stream().anyMatch(o -> clazz.equals(o.clazz))) {
            throw new DuplicateClassException();
        }

        entries.add(new ClassEntry<>(clazz, producer, type));
        return this;
    }

    public <T> DIContainer addService(Class<T> clazz, ServiceType type, Supplier<? extends T> supplier) {
        return addService(clazz, type, (d) -> supplier.get());
    }
}

class ServiceNotFoundException extends RuntimeException {}

class DuplicateClassException extends RuntimeException {}
