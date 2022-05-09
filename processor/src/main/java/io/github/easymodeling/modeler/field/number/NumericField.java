package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.PlainField;

import java.util.Optional;

public abstract class NumericField<T extends Number> extends PlainField<T> {

    protected NumericField() {
    }

    protected NumericField(TypeName type, FieldCustomization customization) {
        super(type, customization);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return constantParameter().orElseGet(this::randomParameter);
    }

    protected CodeBlock randomParameter() {
        return CodeBlock.of("$L, $L", min(), max());
    }

    protected Optional<CodeBlock> constantParameter() {
        return customization.constant()
                .filter(d -> d <= ceiling() && d >= floor())
                .map(this::constantInit)
                .map(init -> CodeBlock.of("$L", init));
    }

    private double min() {
        return customization.min().map(bound -> Math.max(bound, floor())).orElse(0.);
    }

    private double max() {
        return customization.max().map(bound -> Math.min(bound, ceiling())).orElse(ceiling());
    }

    protected abstract double ceiling();

    protected abstract double floor();

    protected abstract CodeBlock constantInit(Double c);
}
