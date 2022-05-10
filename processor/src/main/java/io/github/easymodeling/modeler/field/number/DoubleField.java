package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.number.DoubleRandomizer;

public class DoubleField extends NumericField<Double> {

    public static final ClassName TYPE = ClassName.get(Double.class);

    public DoubleField() {
        this.type = TYPE;
    }

    @Override
    public DoubleField create(FieldCustomization customization, ModelField... valueFields) {
        return new DoubleField(customization);
    }

    private DoubleField(FieldCustomization customization) {
        super(TYPE, customization);
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
