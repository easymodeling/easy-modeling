package xyz.v2my.easymodeling.modeler.field.primitive;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.PlainField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.primitive.BooleanRandomizer;

public class BooleanField extends PlainField<Boolean> {

    public static final ClassName TYPE = ClassName.get(Boolean.class);

    public BooleanField() {
        this.type = TYPE;
    }

    @Override
    public BooleanField create(FieldPattern field, ModelField... valueFields) {
        return new BooleanField(field);
    }

    private BooleanField(FieldPattern field) {
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
