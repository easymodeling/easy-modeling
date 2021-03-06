package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.number.ShortRandomizer;

public class ShortField extends NumericField<Short> {

    public static final ClassName TYPE = ClassName.get(Short.class);

    public ShortField() {
        this.type = TYPE;
    }

    @Override
    public ShortField create(FieldCustomization customization, ModelField... valueFields) {
        return new ShortField(customization);
    }

    private ShortField(FieldCustomization customization) {
        super(TYPE, customization);
    }

    @Override
    protected double ceiling() {
        return Short.MAX_VALUE;
    }

    @Override
    protected double floor() {
        return Short.MIN_VALUE;
    }

    @Override
    protected CodeBlock constantInit(Double c) {
        return CodeBlock.of("(short) $L", c.shortValue());
    }

    @Override
    protected Class<? extends Randomizer<Short>> initializerType() {
        return ShortRandomizer.class;
    }
}
