package io.github.easymodeling;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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

    public static <T> T createModelOf(Class<T> clazz) throws EasyModelingException {
        final List<Constructor<?>> constructors = Arrays.stream(clazz.getDeclaredConstructors())
                .sorted(Comparator.comparingInt(Constructor::getParameterCount))
                .collect(Collectors.toList());
        for (Constructor<?> constructor : constructors) {
            try {
                return createNewInstance(constructor);
            } catch (ReflectiveOperationException | IllegalArgumentException ignored) {
                /* Try with the next available constructor */
            }
        }
        throw new EasyModelingException(String.format("cannot create model for %s", clazz.getSimpleName()));
    }

    private static <T> T createNewInstance(Constructor<?> constructor)
            throws SecurityException, ReflectiveOperationException, IllegalArgumentException {
        constructor.setAccessible(true);
        final Object[] parameters = Arrays.stream(constructor.getParameters())
                .map(p -> defaultValue(p.getType()))
                .toArray();
        @SuppressWarnings("unchecked") final T newInstance = (T) constructor.newInstance(parameters);
        return newInstance;
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
