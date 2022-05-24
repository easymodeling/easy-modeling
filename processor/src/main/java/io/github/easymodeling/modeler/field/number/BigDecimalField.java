package io.github.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.number.BigDecimalRandomizer;

import java.math.BigDecimal;

public class BigDecimalField extends NumericField<BigDecimal> {

    public static final ClassName TYPE = ClassName.get(BigDecimal.class);

    public BigDecimalField() {
        this.type = TYPE;
    }

    @Override
    public ModelField create(FieldCustomization customization, ModelField... valueFields) {
        return new BigDecimalField(customization);
    }

    private BigDecimalField(FieldCustomization customization) {
        super(TYPE, customization);
    }

    @Override
    protected Class<? extends Randomizer<BigDecimal>> initializerType() {
        return BigDecimalRandomizer.class;
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
        return CodeBlock.of("$L", c);
    }
}
