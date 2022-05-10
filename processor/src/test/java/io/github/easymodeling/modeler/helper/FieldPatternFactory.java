package io.github.easymodeling.modeler.helper;

import io.github.easymodeling.modeler.FieldCustomization;
import org.apache.commons.lang3.reflect.FieldUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import static io.github.easymodeling.modeler.field.ModelFieldTest.CLASS_NAME;

public class FieldPatternFactory {

    private final FieldCustomization fieldCustomization;

    private FieldPatternFactory(String className, String fieldName) {
        try {
            final Constructor<FieldCustomization> declaredConstructor = FieldCustomization.class.getDeclaredConstructor(String.class, String.class);
            declaredConstructor.setAccessible(true);
            this.fieldCustomization = declaredConstructor.newInstance(className, fieldName);
        } catch (NoSuchMethodException | InstantiationException | IllegalAccessException |
                 InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    public FieldPatternFactory minSize(Object minSize) {
        return decorate("minSize", minSize);
    }

    public FieldPatternFactory maxSize(Object maxSize) {
        return decorate("maxSize", maxSize);
    }

    public FieldPatternFactory min(Object min) {
        return decorate("min", min);
    }

    public FieldPatternFactory constant(Object constant) {
        return decorate("constant", constant);
    }

    public FieldPatternFactory max(Object max) {
        return decorate("max", max);
    }

    public FieldPatternFactory string(Object string) {
        return decorate("string", string);
    }

    public FieldPatternFactory allowEmpty(Object allowEmpty) {
        return decorate("allowEmpty", allowEmpty);
    }

    public FieldPatternFactory alphanumeric(Object alphanumeric) {
        return decorate("alphanumeric", alphanumeric);
    }

    public FieldPatternFactory alphabetic(Object alphabetic) {
        return decorate("alphabetic", alphabetic);
    }

    public FieldPatternFactory numeric(Object numeric) {
        return decorate("numeric", numeric);
    }

    public FieldPatternFactory now(boolean now) {
        return decorate("now", now);
    }

    public FieldPatternFactory future(boolean future) {
        return decorate("future", future);
    }

    public FieldPatternFactory before(String before) {
        return decorate("before", before);
    }

    public FieldPatternFactory datetime(String datetime) {
        return decorate("datetime", datetime);
    }

    public FieldPatternFactory after(String after) {
        return decorate("after", after);
    }

    public static FieldCustomization any() {
        return FieldPatternFactory.one("field_name").build();
    }

    public static FieldPatternFactory one(String fieldName) {
        return new FieldPatternFactory(CLASS_NAME, fieldName);
    }

    public FieldCustomization build() {
        return fieldCustomization;
    }

    public FieldPatternFactory decorate(String name, Object value) {
        try {
            FieldUtils.writeField(fieldCustomization, name, value, true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
