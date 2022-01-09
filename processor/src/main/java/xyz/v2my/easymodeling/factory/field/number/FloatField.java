package xyz.v2my.easymodeling.factory.field.number;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.FloatRandomizer;

public class FloatField extends NumericField<Float> {

    private FloatField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public FloatField() {
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

    @Override
    public PlainField<Float> create(TypeName type, FieldWrapper field) {
        return new FloatField(type, field);
    }
}