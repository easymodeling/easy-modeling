package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.primitive.BooleanRandomizer;

public class BooleanField extends PlainField<Boolean> {

    public static final ClassName TYPE = ClassName.get(Boolean.class);

    public BooleanField() {
        this.type = TYPE;
    }

    @Override
    public BooleanField create(FieldWrapper field) {
        return new BooleanField(field);
    }

    private BooleanField(FieldWrapper field) {
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
