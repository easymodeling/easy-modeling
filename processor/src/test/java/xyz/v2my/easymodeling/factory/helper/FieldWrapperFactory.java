package xyz.v2my.easymodeling.factory.helper;

import org.apache.commons.lang3.reflect.FieldUtils;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public class FieldWrapperFactory {

    private final FieldWrapper field;

    private FieldWrapperFactory() {
        this.field = FieldWrapper.of("field_name");
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

    public static FieldWrapper any() {
        return FieldWrapperFactory.one().build();
    }

    public static FieldWrapperFactory one() {
        return new FieldWrapperFactory();
    }

    public FieldWrapper build() {
        return field;
    }

    public FieldWrapperFactory decorate(String name, Object value) {
        try {
            FieldUtils.writeField(field, name, value, true);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return this;
    }
}
