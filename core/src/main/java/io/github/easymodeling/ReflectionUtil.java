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
            final QualifiedField qualifiedField = new QualifiedField(qualifiedFieldName);
            final Field field = declaredField(model.getClass(), qualifiedField);
            field.setAccessible(true);
            field.set(model, value);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            throw new EasyModelingException(String.format("Cannot set %s for %s", qualifiedFieldName, model.getClass().getSimpleName()), e);
        }
    }

    public static Object getField(Object model, String qualifiedField) {
        try {
            final Field field = declaredField(model.getClass(), new QualifiedField(qualifiedField));
            field.setAccessible(true);
            return field.get(model);
        } catch (NoSuchFieldException | IllegalAccessException | IllegalArgumentException | SecurityException e) {
            throw new EasyModelingException(String.format("Cannot get %s from %s", qualifiedField, model.getClass().getSimpleName()), e);
        }
    }

    private static Field declaredField(Class<?> clazz, QualifiedField qualifiedField) throws NoSuchFieldException {
        if (clazz.getCanonicalName().equals(qualifiedField.getClassName())) {
            return clazz.getDeclaredField(qualifiedField.getFieldName());
        }
        final Class<?> superclass = clazz.getSuperclass();
        if (Object.class == superclass) {
            throw new NoSuchFieldException(qualifiedField.toString());
        } else {
            return declaredField(superclass, qualifiedField);
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
