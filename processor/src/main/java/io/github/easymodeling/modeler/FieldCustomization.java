package io.github.easymodeling.modeler;

import io.github.easymodeling.log.ProcessorLogger;

import java.time.Instant;
import java.time.format.DateTimeParseException;
import java.util.Optional;

public class FieldCustomization {

    private final String className;

    private final String fieldName;

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

    public FieldCustomization(String className, String fieldName) {
        this.fieldName = fieldName;
        this.className = className;
    }

    public boolean matches(String className, String fieldName) {
        return this.className.equals(className) && this.fieldName.equals(fieldName);
    }

    public FieldCustomization(
            String className, String fieldName,
            Double max, Double min, Double constant,
            // For string related types
            Boolean alphanumeric, Boolean alphabetic, Boolean numeric, String string,
            // For dateTime related types
            boolean now, String before, String after, boolean future, boolean past, String datetime,
            // For collections and streams
            Integer size, Integer minSize, Integer maxSize,
            // For Optionals
            boolean allowEmpty) {
        this.className = className;
        this.fieldName = fieldName;
        this.max = max;
        this.min = min;
        this.constant = constant;
        this.alphanumeric = alphanumeric;
        this.alphabetic = alphabetic;
        this.numeric = numeric;
        this.string = string;
        this.now = now;
        this.before = before;
        this.after = after;
        this.future = future;
        this.past = past;
        this.datetime = datetime;
        this.size = size;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.allowEmpty = allowEmpty;
    }

    public String fieldName() {
        return fieldName;
    }

    public String qualifiedName() {
        return String.format("%s#%s", className, fieldName);
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
        final Optional<Integer> optionalMinSize = isMinSizeSet() ? Optional.of(this.minSize) : Optional.empty();
        return isSizeSet() ? Optional.of(size) : optionalMinSize;
    }

    public Optional<Integer> maxSize() {
        final Optional<Integer> optionalMaxSize = isMaxSizeSet() ? Optional.of(this.maxSize) : Optional.empty();
        return isSizeSet() ? Optional.of(size) : optionalMaxSize;
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
            ProcessorLogger.log.warning("Failed to parse datetime: " + datetime);
            return Optional.empty();
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

    private boolean isDatetimeSet() {
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

    @Override
    public int hashCode() {
        return this.qualifiedName().hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        return this.qualifiedName().equals(((FieldCustomization) o).qualifiedName());
    }
}
