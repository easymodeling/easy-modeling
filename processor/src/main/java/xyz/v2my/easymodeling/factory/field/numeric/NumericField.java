package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;

import java.util.Optional;

public abstract class NumericField<T extends Number> extends PlainField<T> {

    protected NumericField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    protected NumericField() {
    }

    @Override
    protected CodeBlock initializerParameter() {
        return constantParameter().orElseGet(this::randomParameter);
    }

    protected CodeBlock randomParameter() {
        return CodeBlock.of("$L, $L", min(), max());
    }

    protected Optional<CodeBlock> constantParameter() {
        return field.constant()
                .filter(d -> d <= ceiling() && d >= floor())
                .map(this::constantInit)
                .map(init -> CodeBlock.of("$L", init));
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
