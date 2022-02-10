package xyz.v2my.easymodeling.randomizer;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;

public abstract class Modeler<T> {

    protected void setField(T model, String fieldName, Object value) throws IllegalAccessException, NoSuchFieldException {
        final Field field = type().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(model, value);
    }

    protected T next(ModelCache modelCache) {
        if (modelCache == null) {
            modelCache = new ModelCache();
        }
        final Class<T> clazz = type();
        if (modelCache.avoidInfinity(clazz)) {
            return modelCache.first(clazz);
        }
        try {
            return createModel(modelCache);
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    private T createModel(ModelCache modelCache) throws InstantiationException, IllegalAccessException, NoSuchFieldException, InvocationTargetException {
        final Class<T> clazz = type();
        final T model = createModelOf(clazz);
        modelCache.push(model);
        populate(model, modelCache);
        modelCache.pop(clazz);
        return model;
    }

    private T createModelOf(Class<T> clazz) throws InstantiationException, IllegalAccessException, InvocationTargetException {
        final Constructor<?> constructor = Arrays.stream(clazz.getConstructors())
                .min(Comparator.comparingInt(Constructor::getParameterCount))
                .orElseThrow(() -> new IllegalAccessException("No constructor found for " + clazz.getName()));
        final Object[] parameters = Arrays.stream(constructor.getParameters())
                .map(p -> defaultValue(p.getType()))
                .toArray();
        return (T) constructor.newInstance(parameters);
    }

    private Object defaultValue(Class<?> type) {
        if (type.isPrimitive()) {
            if (type == int.class) {
                return 0;
            }
            if (type == long.class) {
                return 0L;
            }
            if (type == double.class) {
                return 0.0;
            }
            if (type == float.class) {
                return 0.0f;
            }
            if (type == short.class) {
                return (short) 0;
            }
            if (type == byte.class) {
                return (byte) 0;
            }
            if (type == boolean.class) {
                return false;
            }
            if (type == char.class) {
                return '\u0000';
            }
        }
        return null;
    }

    protected abstract void populate(T model, ModelCache modelCache) throws NoSuchFieldException, IllegalAccessException;

    protected abstract Class<T> type();
}
