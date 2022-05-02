package io.github.easymodeling.modeler;

import io.github.easymodeling.Field;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class FieldPattern {

    private final String fieldName;

    private final String qualifiedFieldName;

    private Double max = Double.NaN;

    private Double min = Double.NaN;

    private Double constant = Double.NaN;

    private Boolean alphanumeric = true;

    private Boolean alphabetic = false;

    private Boolean numeric = false;

    private String string = "";

    private boolean now = false;

    private String before = "";

    private String after = "";

    private boolean past = false;

    private boolean future = false;

    private String datetime = "";

    private Integer size = Integer.MAX_VALUE;

    private Integer minSize = Integer.MAX_VALUE;

    private Integer maxSize = Integer.MAX_VALUE;

    boolean allowEmpty = false;

    public static FieldPattern of(String className, String fieldName) {
        return new FieldPattern(className, fieldName);
    }

    private FieldPattern(String className, String fieldName) {
        this.fieldName = fieldName;
        this.qualifiedFieldName = nameOf(className, fieldName);
    }

    public static FieldPattern of(String className, Field field) {
        return new FieldPattern(className, field);
    }

    private FieldPattern(String className, Field annotation) {
        this.fieldName = annotation.name();
        this.qualifiedFieldName = nameOf(className, annotation.name());
        this.max = annotation.max();
        this.min = annotation.min();
        this.constant = annotation.constant();
        this.alphanumeric = annotation.alphanumeric();
        this.alphabetic = annotation.alphabetic();
        this.numeric = annotation.numeric();
        this.string = annotation.string();
        this.now = annotation.now();
        this.before = annotation.before();
        this.after = annotation.after();
        this.future = annotation.future();
        this.past = annotation.past();
        this.datetime = annotation.datetime();
        this.size = annotation.size();
        this.minSize = annotation.minSize();
        this.maxSize = annotation.maxSize();
        this.allowEmpty = annotation.allowEmpty();
    }

    public String fieldName() {
        return fieldName;
    }

    public String qualifiedName() {
        return qualifiedFieldName;
    }

    public boolean alphanumeric() {
        return alphanumeric;
    }

    public boolean alphabetic() {
        return alphabetic;
    }

    public boolean numeric() {
        return numeric;
    }

    public Optional<Double> min() {
        return this.isMinSet() ? Optional.of(min) : Optional.empty();
    }

    public Optional<Double> max() {
        return this.isMaxSet() ? Optional.of(max) : Optional.empty();
    }

    public Optional<Double> constant() {
        return this.isConstantSet() ? Optional.of(constant) : Optional.empty();
    }

    public Optional<String> string() {
        return this.isStringSet() ? Optional.of(string) : Optional.empty();
    }

    public Optional<Instant> before() {
        if (future) {
            return Optional.of(Instant.now());
        }
        return this.isBeforeSet() ? Optional.of(before).map(Instant::parse) : Optional.empty();
    }

    public Optional<Instant> after() {
        if (past) {
            return Optional.of(Instant.now());
        }
        return this.isAfterSet() ? Optional.of(after).map(Instant::parse) : Optional.empty();
    }

    public boolean now() {
        return now;
    }

    public Optional<Integer> minSize() {
        final Optional<Integer> minSize = isMinSizeSet() ? Optional.of(this.minSize) : Optional.empty();
        return isSizeSet() ? Optional.of(size) : minSize;
    }

    public Optional<Integer> maxSize() {
        final Optional<Integer> maxSize = isMaxSizeSet() ? Optional.of(this.maxSize) : Optional.empty();
        return isSizeSet() ? Optional.of(size) : maxSize;
    }

    public Optional<Instant> datetime() {
        return this.isDatetimeSet() ? dateTimeParsed() : Optional.empty();
    }

    public boolean allowEmpty() {
        return allowEmpty;
    }

    private Optional<Instant> dateTimeParsed() {
        try {
            return Optional.of(datetime).map(Instant::parse);
        } catch (DateTimeParseException e) {
            // TODO: 29.12.21 ignore (return Optional.empty()) or throw more specific exception
            throw new IllegalArgumentException("datetime format error", e);
        }
    }

    private boolean isMinSet() {
        return !min.isNaN();
    }

    private boolean isMaxSet() {
        return !max.isNaN();
    }

    private boolean isConstantSet() {
        return !constant.isNaN();
    }

    private boolean isStringSet() {
        return !string.isEmpty();
    }

    private boolean isBeforeSet() {
        return !before.isEmpty();
    }

    private boolean isAfterSet() {
        return !after.isEmpty();
    }

    public boolean isDatetimeSet() {
        return !datetime.isEmpty();
    }

    private boolean isSizeSet() {
        return size != Integer.MAX_VALUE && size >= 0;
    }

    private boolean isMinSizeSet() {
        return minSize != Integer.MAX_VALUE && minSize >= 0;
    }

    private boolean isMaxSizeSet() {
        return maxSize != Integer.MAX_VALUE && maxSize >= 0;
    }

    private String nameOf(String className, String fieldName) {
        return String.format("%s#%s", className, fieldName);
    }

}
