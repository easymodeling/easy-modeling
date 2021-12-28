package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.numberrandomizer.ByteRandomizer;

public class ByteField extends NumericField {

    public ByteField(TypeName type, String name, Field field) {
        super(type, name, field);
    }

    @Override
    protected long ceiling() {
        return Byte.MAX_VALUE;
    }

    @Override
    protected long floor() {
        return Byte.MIN_VALUE;
    }

    @Override
    protected CodeBlock constantInit(Double c) {
        return CodeBlock.of("(byte) $L", c.byteValue());
    }

    @Override
    protected Class<? extends Randomizer> randomizer() {
        return ByteRandomizer.class;
    }
}
