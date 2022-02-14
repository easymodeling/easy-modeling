package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.number.ByteRandomizer;

public class ByteField extends NumericField<Byte> {

    public static final ClassName TYPE = ClassName.get(Byte.class);

    public ByteField() {
        this.type = TYPE;
    }

    @Override
    public ByteField create(FieldPattern field, ModelField... valueFields) {
        return new ByteField(field);
    }

    private ByteField(FieldPattern field) {
        super(TYPE, field);
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
}
