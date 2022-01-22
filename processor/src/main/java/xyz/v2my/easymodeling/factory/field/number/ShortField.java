package xyz.v2my.easymodeling.factory.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.ShortRandomizer;

public class ShortField extends NumericField<Short> {

    public static final ClassName TYPE = ClassName.get(Short.class);

    public ShortField() {
        this.type = TYPE;
    }

    @Override
    public ShortField create(FieldWrapper field, ModelField... valueFields) {
        return new ShortField(field);
    }

    private ShortField(FieldWrapper field) {
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
