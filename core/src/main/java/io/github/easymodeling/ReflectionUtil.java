package io.github.easymodeling;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Comparator;

public class ReflectionUtil {

    private ReflectionUtil() {
    }

    public static void setField(Object model, String qualifiedFieldName, Object value) {
        try {
            final int splitter = qualifiedFieldName.lastIndexOf('#');
            if (splitter == -1 || splitter == qualifiedFieldName.length() - 1 || splitter == 0) {
                throw new NoSuchFieldException(qualifiedFieldName);
            }
            String className = qualifiedFieldName.substring(0, splitter);
            String fieldName = qualifiedFieldName.substring(splitter + 1);

            final Field field = declaredField(model.getClass(), className, fieldName);
            field.setAccessible(true);
            field.set(model, value);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            throw new EasyModelingException(String.format("Cannot set %s for %s", qualifiedFieldName, model.getClass().getSimpleName()), e);
        }
    }

    private static Field declaredField(Class<?> clazz, String className, String fieldName) throws NoSuchFieldException {
        if (clazz.getCanonicalName().equals(className)) {
            return clazz.getDeclaredField(fieldName);
        }
        final Class<?> superclass = clazz.getSuperclass();
        if (Object.class == superclass) {
            throw new NoSuchFieldException(className + "#" + fieldName);
        } else {
            return declaredField(superclass, className, fieldName);
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
        if (boolean.class.equals(type)) {
            return false;
        }
        if (type.isPrimitive()) {
            return 0;
        }
        return null;
    }
}
