package xyz.v2my.easymodeling;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;

public class ReflectionUtil {

    public static void setField(Object model, String fieldName, Object value) {
        try {
            final Field field = model.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            field.set(model, value);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            throw new EasyModelingException(String.format("Cannot set %s for %s", fieldName, model.getClass().getSimpleName()), e);
        }
    }

    public static Object getField(Object model, String fieldName) {
        try {
            final Field field = model.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(model);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            throw new EasyModelingException(String.format("Cannot get %s from %s", fieldName, model.getClass().getSimpleName()), e);
        }
    }

    public static <T> T createModelOf(Class<T> clazz) {
        try {
            final Constructor<?> constructor = Arrays.stream(clazz.getDeclaredConstructors())
                    .min(Comparator.comparingInt(Constructor::getParameterCount))
                    .orElseThrow(() -> new IllegalAccessException("No constructor found for " + clazz.getName()));
            constructor.setAccessible(true);
            final Object[] parameters = Arrays.stream(constructor.getParameters())
                    .map(p -> defaultValue(p.getType()))
                    .toArray();
            return (T) constructor.newInstance(parameters);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException e) {
            throw new EasyModelingException(String.format("cannot create model for %s", clazz.getSimpleName()), e);
        }
    }

    private static Object defaultValue(Class<?> type) {
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
}
