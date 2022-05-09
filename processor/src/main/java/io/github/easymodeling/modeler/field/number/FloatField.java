package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.number.FloatRandomizer;

public class FloatField extends NumericField<Float> {

    public static final ClassName TYPE = ClassName.get(Float.class);

    public FloatField() {
        this.type = TYPE;
    }

    @Override
    public FloatField create(FieldCustomization customization, ModelField... valueFields) {
        return new FloatField(customization);
    }

    private FloatField(FieldCustomization customization) {
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
        return CodeBlock.of("$LF", c.floatValue());
    }

    @Override
    protected Class<? extends Randomizer<Float>> initializerType() {
        return FloatRandomizer.class;
    }
}
