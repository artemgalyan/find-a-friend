package by.fpmibsu.findafriend.application.serviceproviders;

import by.fpmibsu.findafriend.application.mediatr.HandlersDataList;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class DefaultGlobalServiceProvider implements GlobalServiceProvider {

    private static class ClassEntry<T> {
        public Class<T> clazz;
        public Function<ServiceProvider, ? extends T> producer;
        public ServiceType type;

        public ClassEntry(Class<T> clazz, Function<ServiceProvider, ? extends T> producer, ServiceType type) {
            this.clazz = clazz;
            this.producer = producer;
            this.type = type;
        }
    }

    private static class ConstructedObject<T> {
        public Class<T> registeredClass;
        public T instance;

        public ConstructedObject(Class<T> registeredClass, T instance) {
            this.registeredClass = registeredClass;
            this.instance = instance;
        }
    }

    private final List<ClassEntry<?>> entries = new ArrayList<>();
    private final List<ConstructedObject<?>> singletons = new ArrayList<>();

    @Override
    public <T> T getRequiredService(Class<T> clazz) {
        var singleton = singletons.stream().filter(o -> clazz.equals(o.registeredClass)).findFirst();
        if (singleton.isPresent()) {
            return (T) singleton.get().instance;
        }
        var item = entries.stream().filter(o -> clazz.equals(o.clazz)).findFirst();
        if (item.isEmpty()) {
            throw new ServiceNotFoundException("Can't find service of type " + clazz.getName());
        }

        if (item.get().type == ServiceType.SINGLETON) {
            var object = (T) item.get().producer.apply(this);
            singletons.add(new ConstructedObject<>(clazz, object));
            return object;
        }

        return (T) item.get().producer.apply(this);
    }

    @Override
    public <T> DefaultGlobalServiceProvider addService(Class<T> clazz, ServiceType type, Function<ServiceProvider, ? extends T> producer) {
        if (entries.stream().anyMatch(o -> clazz.equals(o.clazz))) {
            throw new ServiceAlreadyRegisteredException("Service of type " + clazz.getName() + " is already registered");
        }

        entries.add(new ClassEntry<>(clazz, producer, type));
        return this;
    }

    private Optional<ClassEntry<?>> findClassEntry(Class<?> clazz) {
        return entries.stream().filter(o -> clazz.equals(o.clazz)).findFirst();
    }
}