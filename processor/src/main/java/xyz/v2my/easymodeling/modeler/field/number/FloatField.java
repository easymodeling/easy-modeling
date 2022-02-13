package xyz.v2my.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.FloatRandomizer;

public class FloatField extends NumericField<Float> {

    public static final ClassName TYPE = ClassName.get(Float.class);

    public FloatField() {
        this.type = TYPE;
    }

    @Override
    public FloatField create(FieldPattern field, ModelField... valueFields) {
        return new FloatField(field);
    }

    private FloatField(FieldPattern field) {
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
        return CodeBlock.of("$LF", c.floatValue());
    }

    @Override
    protected Class<? extends Randomizer<Float>> initializerType() {
        return FloatRandomizer.class;
    }
}