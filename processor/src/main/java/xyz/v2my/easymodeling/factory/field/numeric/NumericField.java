package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.factory.field.AbstractField;

import java.util.Optional;

public abstract class NumericField extends AbstractField {

    protected final Field field;

    public NumericField(TypeName type, String name, Field field) {
        super(type, name);
        this.field = field;
    }

    @Override
    public CodeBlock initializer() {
        return constantInit().orElseGet(this::randomInit);
    }

    private Optional<CodeBlock> constantInit() {
        return fieldConstant()
                .filter(d -> d <= ceiling() && d >= floor())
                .map(this::constantInit);
    }

    private CodeBlock randomInit() {
        return CodeBlock.of("new $T().next($L, $L)", randomizer(), min(), max());
    }

    private double min() {
        return fieldMin().map(bound -> Math.max(bound, floor())).orElse(0.);
    }

    private double max() {
        return fieldMax().map(bound -> Math.min(bound, ceiling())).orElse(ceiling());
    }

    private Optional<Double> fieldMin() {
        return Optional.ofNullable(field).map(Field::min).filter(d -> !d.isNaN());
    }

    private Optional<Double> fieldMax() {
        return Optional.ofNullable(field).map(Field::max).filter(d -> !d.isNaN());
    }

    private Optional<Double> fieldConstant() {
        return Optional.ofNullable(field).map(Field::constant).filter(d -> !d.isNaN());
    }

    protected abstract double ceiling();

    protected abstract double floor();

    protected abstract CodeBlock constantInit(Double c);
}
