package io.github.easymodeling.modeler.field.primitive;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.primitive.BooleanRandomizer;

public class BooleanField extends PlainField<Boolean> {

    public static final ClassName TYPE = ClassName.get(Boolean.class);

    public BooleanField() {
        this.type = TYPE;
    }

    @Override
    public BooleanField create(FieldCustomization field, ModelField... valueFields) {
        return new BooleanField(field);
    }

    private BooleanField(FieldCustomization field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<Boolean>> initializerType() {
        return BooleanRandomizer.class;
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("");
    }
}
