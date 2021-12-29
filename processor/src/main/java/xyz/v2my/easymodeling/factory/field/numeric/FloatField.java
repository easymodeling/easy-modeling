package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.numberrandomizer.FloatRandomizer;

public class FloatField extends NumericField {

    protected FloatField(TypeName type, FieldWrapper field) {
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
    protected Class<? extends Randomizer<?>> randomizer() {
        return FloatRandomizer.class;
    }

    @Override
    public ModelField create(TypeName type, FieldWrapper field) {
        return new FloatField(type, field);
    }
}
