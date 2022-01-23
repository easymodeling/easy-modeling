package xyz.v2my.easymodeling.factory.helper;

import org.apache.commons.lang3.reflect.FieldUtils;
import xyz.v2my.easymodeling.factory.FieldPattern;

public class FieldPatternFactory {

    private final FieldPattern field;

    private FieldPatternFactory(String name) {
        this.field = FieldPattern.of(name);
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

    public FieldPatternFactory before(String before) {
        return decorate("before", before);
    }

    public FieldPatternFactory datetime(String datetime) {
        return decorate("datetime", datetime);
    }

    public FieldPatternFactory after(String after) {
        return decorate("after", after);
    }

    public static FieldPattern any() {
        return FieldPatternFactory.one("field_name").build();
    }

    public static FieldPatternFactory one(String name) {
        return new FieldPatternFactory(name);
    }

    public FieldPattern build() {
        return field;
    }

    public FieldPatternFactory decorate(String name, Object value) {
        try {
            FieldUtils.writeField(field, name, value, true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        return this;
    }
}
