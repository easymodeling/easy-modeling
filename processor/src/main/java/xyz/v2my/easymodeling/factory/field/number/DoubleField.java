package xyz.v2my.easymodeling.factory.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.DoubleRandomizer;

public class DoubleField extends NumericField<Double> {

    public static final ClassName TYPE = ClassName.get(Double.class);

    public DoubleField() {
        this.type = TYPE;
    }

    @Override
    public DoubleField create(FieldWrapper field, ModelField... valueFields) {
        return new DoubleField(field);
    }

    private DoubleField(FieldWrapper field) {
        super(TYPE, field);
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
