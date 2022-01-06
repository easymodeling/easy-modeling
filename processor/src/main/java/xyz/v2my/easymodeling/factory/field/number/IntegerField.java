package xyz.v2my.easymodeling.factory.field.number;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

public class IntegerField extends NumericField<Integer> {

    private IntegerField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public IntegerField() {
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

    @Override
    public PlainField<Integer> create(TypeName type, FieldWrapper field) {
        return new IntegerField(type, field);
    }
}
