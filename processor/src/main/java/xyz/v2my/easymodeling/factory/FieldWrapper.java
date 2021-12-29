package xyz.v2my.easymodeling.factory;

import xyz.v2my.easymodeling.Field;

import java.time.Instant;
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
        return this.isBeforeSet() ? Optional.of(before).map(Instant::parse) : Optional.empty();
    }

    public Optional<Instant> after() {
        return this.isAfterSet() ? Optional.of(after).map(Instant::parse) : Optional.empty();
    }

    public boolean now() {
        return now;
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

}
