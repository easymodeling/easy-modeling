package io.github.easymodeling.modeler.field.primitive;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.primitive.CharRandomizer;

public class CharField extends PlainField<Character> {

    public static final ClassName TYPE = ClassName.get(Character.class);

    public CharField() {
        this.type = TYPE;
    }

    @Override
    public CharField create(FieldPattern field, ModelField... valueFields) {
        return new CharField(field);
    }

    private CharField(FieldPattern field) {
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
