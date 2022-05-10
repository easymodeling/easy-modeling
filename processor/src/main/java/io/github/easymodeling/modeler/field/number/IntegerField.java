package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.number.IntegerRandomizer;

public class IntegerField extends NumericField<Integer> {

    public static final ClassName TYPE = ClassName.get(Integer.class);

    public IntegerField() {
        this.type = TYPE;
    }

    @Override
    public IntegerField create(FieldCustomization customization, ModelField... valueFields) {
        return new IntegerField(customization);
    }

    private IntegerField(FieldCustomization customization) {
        super(TYPE, customization);
    }

    @Override
    protected double ceiling() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected double floor() {
        return Integer.MIN_VALUE;
    }

    @Override
    protected CodeBlock constantInit(Double c) {
        return CodeBlock.of("$L", c.intValue());
    }

    @Override
    protected Class<? extends Randomizer<Integer>> initializerType() {
        return IntegerRandomizer.class;
    }
}
