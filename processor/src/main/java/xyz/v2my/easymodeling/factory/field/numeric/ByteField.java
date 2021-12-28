package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.numberrandomizer.ByteRandomizer;

public class ByteField extends NumericField {

    protected ByteField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public ByteField() {
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
    protected Class<? extends Randomizer> randomizer() {
        return ByteRandomizer.class;
    }

    @Override
    public ModelField create(TypeName type, FieldWrapper field) {
        return new ByteField(type, field);
    }
}
