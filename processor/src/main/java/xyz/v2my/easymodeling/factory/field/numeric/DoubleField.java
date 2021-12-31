package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.DoubleRandomizer;

public class DoubleField extends NumericField<Double> {

    private DoubleField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public DoubleField() {
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

    @Override
    public ModelField<Double> create(TypeName type, FieldWrapper field) {
        return new DoubleField(type, field);
    }
}
