package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.ShortRandomizer;

public class ShortField extends NumericField<Short> {

    private ShortField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public ShortField() {
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

    @Override
    public ModelField<Short> create(TypeName type, FieldWrapper field) {
        return new ShortField(type, field);
    }
}
