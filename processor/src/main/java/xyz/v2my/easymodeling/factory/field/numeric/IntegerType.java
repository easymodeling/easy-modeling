package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainType;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.IntegerRandomizer;

public class IntegerType extends NumericType<Integer> {

    private IntegerType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public IntegerType() {
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
    public PlainType<Integer> create(TypeName type, FieldWrapper field) {
        return new IntegerType(type, field);
    }
}
