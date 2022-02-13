package xyz.v2my.easymodeling.modeler.field.number;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.number.LongRandomizer;

public class LongField extends NumericField<Long> {

    public static final ClassName TYPE = ClassName.get(Long.class);

    public LongField() {
        this.type = TYPE;
    }

    @Override
    public LongField create(FieldPattern field, ModelField... valueFields) {
        return new LongField(field);
    }

    private LongField(FieldPattern field) {
        super(TYPE, field);
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
    protected Class<? extends Randomizer<Long>> initializerType() {
        return LongRandomizer.class;
    }
}