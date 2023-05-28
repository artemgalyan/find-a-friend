package by.fpmibsu.findafriend.application.serviceproviders;

import by.fpmibsu.findafriend.application.mediatr.HandlersDataList;
import by.fpmibsu.findafriend.application.mediatr.Mediatr;

import java.util.HashMap;
import java.util.Map;
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

    private final Map<Class<?>, ClassEntry<?>> entries = new HashMap<>();
    private final Map<Class<?>, ConstructedObject<?>> singletons = new HashMap<>();

    @Override
    public <T> T getRequiredService(Class<T> clazz) {
        var entry = tryGetObject(clazz);
        if (entry.isEmpty()) {
            throw new ServiceNotFoundException("Can't find service of type " + clazz.getName());
        }
        var type = entries.get(clazz).type;
        if (type == ServiceType.SINGLETON) {
            singletons.put(clazz, new ConstructedObject<>(clazz, entry.get()));
        }
        return entry.get();
    }

    @Override
    public <T> boolean hasService(Class<T> clazz) {
        return entries.containsKey(clazz);
    }

    @Override
    public <T> DefaultGlobalServiceProvider addService(Class<T> clazz, ServiceType type, Function<ServiceProvider, ? extends T> producer) {
        if (entries.containsKey(clazz)) {
            throw new ServiceAlreadyRegisteredException("Service of type " + clazz.getName() + " is already registered");
        }

        entries.put(clazz, new ClassEntry<>(clazz, producer, type));
        return this;
    }

    @Override
    public ScopedServiceProvider getRequestServiceProvider() {
        return new RequestServiceProvider();
    }

    private Optional<ClassEntry<?>> findClassEntry(Class<?> clazz) {
        return Optional.ofNullable(entries.get(clazz));
    }

    private <T> Optional<T> tryGetObject(Class<T> clazz) {
        if (singletons.containsKey(clazz)) {
            return Optional.of((T) singletons.get(clazz).instance);
        }
        if (entries.containsKey(clazz)) {
            return Optional.of(
                    (T) entries.get(clazz).producer.apply(this)
            );
        }
        return Optional.empty();
    }

    public class RequestServiceProvider implements ScopedServiceProvider {
        private final Map<Class<?>, ConstructedObject<?>> scoped = new HashMap<>();

        public RequestServiceProvider() {
            addScoped(ScopedServiceProvider.class, this);
            addScoped(ServiceProvider.class, this);
            if (hasService(HandlersDataList.class)) {
                addScoped(Mediatr.class, new Mediatr(this, getRequiredService(HandlersDataList.class)));
            }
        }

        @Override
        public <T> T getRequiredService(Class<T> clazz) {
            if (scoped.containsKey(clazz)) {
                return (T) scoped.get(clazz).instance;
            }

            var entry = findClassEntry(clazz);
            if (entry.isEmpty()) {
                throw new ServiceNotFoundException("Can't find service of type " + clazz.getName());
            }

            var e = entry.get();
            var result = DefaultGlobalServiceProvider.this.getRequiredService(clazz);
            if (e.type == ServiceType.SCOPED) {
                scoped.put(clazz, new ConstructedObject<>(clazz, result));
            }
            return result;
        }

        @Override
        public <T> boolean hasService(Class<T> clazz) {
            return DefaultGlobalServiceProvider.this.hasService(clazz);
        }

        @Override
        public <T> ScopedServiceProvider addScoped(Class<T> clazz, T instance) {
            if (scoped.containsKey(clazz)) {
                throw new ServiceAlreadyRegisteredException("Service of type " + clazz.getName() + " is already registered");
            }
            scoped.put(clazz, new ConstructedObject<>(clazz, instance));
            return this;
        }

        @Override
        public void close() throws Exception {
            for (ConstructedObject<?> entry : scoped.values()) {
                var instance = entry.instance;
                if (instance != this && instance instanceof AutoCloseable ac) {
                    ac.close();
                }
            }
        }
    }

}
