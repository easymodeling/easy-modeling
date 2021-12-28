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
                .filter(d -> !d.isNaN())
                .filter(d -> d <= ceiling() && d >= floor())
                .map(this::constantInit);
    }

    private CodeBlock randomInit() {
        return CodeBlock.of("new $T().next($LL, $LL)", randomizer(), min(), max());
    }

    private long min() {
        return fieldMin().map(bound -> Math.max(bound, floor())).orElse(0L);
    }

    private long max() {
        return fieldMax().map(bound -> Math.min(bound, ceiling())).orElse(ceiling());
    }

    private Optional<Long> fieldMin() {
        return Optional.ofNullable(field).map(Field::min);
    }

    private Optional<Long> fieldMax() {
        return Optional.ofNullable(field).map(Field::max);
    }

    private Optional<Double> fieldConstant() {
        return Optional.ofNullable(field).map(Field::constant);
    }

    protected abstract long ceiling();

    protected abstract long floor();

    protected abstract CodeBlock constantInit(Double c);
}
