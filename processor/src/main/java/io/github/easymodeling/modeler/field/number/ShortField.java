package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.number.ShortRandomizer;

public class ShortField extends NumericField<Short> {

    public static final ClassName TYPE = ClassName.get(Short.class);

    public ShortField() {
        this.type = TYPE;
    }

    @Override
    public ShortField create(FieldPattern field, ModelField... valueFields) {
        return new ShortField(field);
    }

    private ShortField(FieldPattern field) {
        super(TYPE, field);
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
