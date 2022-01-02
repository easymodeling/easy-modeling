package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainType;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.ByteRandomizer;

public class ByteType extends NumericType<Byte> {

    private ByteType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public ByteType() {
    }

    @Override
    protected double ceiling() {
        return Byte.MAX_VALUE;
    }

    @Override
    protected double floor() {
        return Byte.MIN_VALUE;
    }

    @Override
    protected CodeBlock constantInit(Double c) {
        return CodeBlock.of("(byte) $L", c.byteValue());
    }

    @Override
    protected Class<? extends Randomizer<Byte>> initializerType() {
        return ByteRandomizer.class;
    }

    @Override
    public PlainType<Byte> create(TypeName type, FieldWrapper field) {
        return new ByteType(type, field);
    }
}
