package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.numberrandomizer.LongRandomizer;

public class LongField extends NumericField {

    public LongField(TypeName type, String name, Field field) {
        super(type, name, field);
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
        return CodeBlock.of("$LL", c.longValue());
    }

    @Override
    protected Class<? extends Randomizer> randomizer() {
        return LongRandomizer.class;
    }
}
