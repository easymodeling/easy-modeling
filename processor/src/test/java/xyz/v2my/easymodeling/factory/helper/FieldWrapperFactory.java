package xyz.v2my.easymodeling.factory.helper;

import org.apache.commons.lang3.reflect.FieldUtils;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public class FieldWrapperFactory {

    private final FieldWrapper field;

    private FieldWrapperFactory(String name) {
        this.field = FieldWrapper.of(name);
    }

    public FieldWrapperFactory minLength(Object minLength) {
        return decorate("minLength", minLength);
    }

    public FieldWrapperFactory maxLength(Object maxLength) {
        return decorate("maxLength", maxLength);
    }

    public FieldWrapperFactory min(Object min) {
        return decorate("min", min);
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
