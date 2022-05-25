package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.number.BigIntegerRandomizer;

import java.math.BigInteger;

public class BigIntegerField extends NumericField<BigInteger> {

    public static final ClassName TYPE = ClassName.get(BigInteger.class);

    public BigIntegerField() {
        this.type = TYPE;
    }

    @Override
    public ModelField create(FieldCustomization customization, ModelField... valueFields) {
        return new BigIntegerField(customization);
    }

    private BigIntegerField(FieldCustomization customization) {
        super(TYPE, customization);
    }

    @Override
    protected Class<? extends Randomizer<BigInteger>> initializerType() {
        return BigIntegerRandomizer.class;
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
}
