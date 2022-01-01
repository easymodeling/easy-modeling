package xyz.v2my.easymodeling.factory;

import xyz.v2my.easymodeling.Field;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class FieldWrapper {

    private final String name;

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

    private Integer length = Integer.MAX_VALUE;

    private Integer minLength = Integer.MAX_VALUE;

    private Integer maxLength = Integer.MAX_VALUE;

    boolean allowEmpty = false;

    public static FieldWrapper of(String name) {
        return new FieldWrapper(name);
    }

    private FieldWrapper(String name) {
        this.name = name;
    }

    public static FieldWrapper of(Field field) {
        return new FieldWrapper(field);
    }

    private FieldWrapper(Field annotation) {
        this.name = annotation.name();
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
        this.length = annotation.length();
        this.minLength = annotation.minLength();
        this.maxLength = annotation.maxLength();
        this.allowEmpty = annotation.allowEmpty();
    }

    public String name() {
        return name;
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

    public Optional<Integer> minLength() {
        return isLengthSet() ? Optional.of(length) : isMinLengthSet() ? Optional.of(minLength) : Optional.empty();
    }

    public Optional<Integer> maxLength() {
        return isLengthSet() ? Optional.of(length) : isMaxLengthSet() ? Optional.of(maxLength) : Optional.empty();
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

    private boolean isLengthSet() {
        return length != Integer.MAX_VALUE && length >= 0;
    }

    private boolean isMinLengthSet() {
        return minLength != Integer.MAX_VALUE && minLength >= 0;
    }

    private boolean isMaxLengthSet() {
        return maxLength != Integer.MAX_VALUE && maxLength >= 0;
    }

}
