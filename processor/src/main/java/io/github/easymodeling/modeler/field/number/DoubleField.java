package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.number.DoubleRandomizer;

public class DoubleField extends NumericField<Double> {

    public static final ClassName TYPE = ClassName.get(Double.class);

    public DoubleField() {
        this.type = TYPE;
    }

    @Override
    public DoubleField create(FieldPattern field, ModelField... valueFields) {
        return new DoubleField(field);
    }

    private DoubleField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected double ceiling() {
        return Long.MAX_VALUE;
    }

    @Override
    protected double floor() {
        return Long.MIN_VALUE;
    }

    @Override
    protected CodeBlock constantInit(Double c) {
        return CodeBlock.of("$L", c);
    }

    @Override
    protected Class<? extends Randomizer<Double>> initializerType() {
        return DoubleRandomizer.class;
    }
}
