package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.primitive.CharRandomizer;

public class CharField extends PlainField<Character> {

    public static final ClassName TYPE = ClassName.get(Character.class);

    public CharField() {
        this.type = TYPE;
    }

    @Override
    public CharField create(FieldWrapper field, ModelField... valueFields) {
        return new CharField(field);
    }

    private CharField(FieldWrapper field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<Character>> initializerType() {
        return CharRandomizer.class;
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("");
    }
}
