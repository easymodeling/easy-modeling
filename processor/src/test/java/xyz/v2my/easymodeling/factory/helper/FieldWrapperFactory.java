package xyz.v2my.easymodeling.factory.helper;

import org.apache.commons.lang3.reflect.FieldUtils;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public class FieldWrapperFactory {

    private final FieldWrapper field;

    private FieldWrapperFactory(String name) {
        this.field = FieldWrapper.of(name);
    }

    public FieldWrapperFactory minSize(Object minSize) {
        return decorate("minSize", minSize);
    }

    public FieldWrapperFactory maxSize(Object maxSize) {
        return decorate("maxSize", maxSize);
    }

    public FieldWrapperFactory min(Object min) {
        return decorate("min", min);
    }

    public FieldWrapperFactory constant(Object constant) {
        return decorate("constant", constant);
    }

    public FieldWrapperFactory max(Object max) {
        return decorate("max", max);
    }

    public FieldWrapperFactory string(Object string) {
        return decorate("string", string);
    }

    public FieldWrapperFactory allowEmpty(Object allowEmpty) {
        return decorate("allowEmpty", allowEmpty);
    }

    public FieldWrapperFactory alphanumeric(Object alphanumeric) {
        return decorate("alphanumeric", alphanumeric);
    }

    public FieldWrapperFactory alphabetic(Object alphabetic) {
        return decorate("alphabetic", alphabetic);
    }

    public FieldWrapperFactory numeric(Object numeric) {
        return decorate("numeric", numeric);
    }

    public FieldWrapperFactory now(boolean now) {
        return decorate("now", now);
    }

    public static FieldWrapper any() {
        return FieldWrapperFactory.one("field_name").build();
    }

    public static FieldWrapperFactory one(String name) {
        return new FieldWrapperFactory(name);
    }

    public FieldWrapper build() {
        return field;
    }

    public FieldWrapperFactory decorate(String name, Object value) {
        try {
            FieldUtils.writeField(field, name, value, true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
