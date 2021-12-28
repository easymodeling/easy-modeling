package xyz.v2my.easymodeling.factory;

import xyz.v2my.easymodeling.Field;

import java.util.Optional;

public class FieldWrapper {

    // @formatter:off
    public static final double DEFAULT_MAX          = Double.NaN;
    public static final double DEFAULT_MIN          = Double.NaN;
    public static final double DEFAULT_CONSTANT     = Double.NaN;
    public static final String DEFAULT_STRING       = "";
    // @formatter:on

    private String name;

    private Double max = DEFAULT_MAX;

    private Double min = DEFAULT_MIN;

    private Double constant = DEFAULT_CONSTANT;

    private Boolean alphanumeric = true;

    private Boolean alphabetic = false;

    private Boolean numeric = false;

    private String string = DEFAULT_STRING;

    public static final FieldWrapper EMPTY = new FieldWrapper();

    public FieldWrapper() {
    }

    public FieldWrapper(Field annotation) {
        this.name = annotation.name();
        this.max = annotation.max();
        this.min = annotation.min();
        this.constant = annotation.constant();
        this.alphanumeric = annotation.alphanumeric();
        this.alphabetic = annotation.alphabetic();
        this.numeric = annotation.numeric();
        this.string = annotation.string();
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
}
