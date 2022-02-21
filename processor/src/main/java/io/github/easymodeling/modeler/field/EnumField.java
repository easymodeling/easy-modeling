package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.randomizer.EnumRandomizer;

public class EnumField extends ModelField {

    public EnumField(TypeName type, FieldPattern field) {
        super(type, field);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T<>($T.values())", EnumRandomizer.class, type);
    }

    @Override
    public ModelField create(FieldPattern field, ModelField... valueFields) {
        return null;
    }
}
