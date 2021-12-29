package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;

import java.util.Optional;

public abstract class NumericField extends ModelField {

    protected NumericField(TypeName type, FieldWrapper field) {
        this.type = type;
        this.name = field.name();
        this.field = field;
    }

    public NumericField() {
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T($L, $L)", randomizer(), min(), max());
    }

    @Override
    protected Optional<CodeBlock> constantInitialization() {
        return field.constant()
                .filter(d -> d <= ceiling() && d >= floor())
                .map(this::constantInit);
    }

    private double min() {
        return field.min().map(bound -> Math.max(bound, floor())).orElse(0.);
    }

    private double max() {
        return field.max().map(bound -> Math.min(bound, ceiling())).orElse(ceiling());
    }

    protected abstract double ceiling();

    protected abstract double floor();

    protected abstract CodeBlock constantInit(Double c);
}
