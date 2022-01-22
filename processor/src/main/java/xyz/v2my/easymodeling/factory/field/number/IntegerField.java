package xyz.v2my.easymodeling.factory.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

public class IntegerField extends NumericField<Integer> {

    public static final ClassName TYPE = ClassName.get(Integer.class);

    public IntegerField() {
        this.type = TYPE;
    }

    @Override
    public IntegerField create(FieldWrapper field, ModelField... valueFields) {
        return new IntegerField(field);
    }

    private IntegerField(FieldWrapper field) {
        super(TYPE, field);
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
