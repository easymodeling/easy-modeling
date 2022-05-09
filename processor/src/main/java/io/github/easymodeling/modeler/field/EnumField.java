package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.randomizer.EnumRandomizer;

public class EnumField extends ModelField {

    public EnumField(TypeName type, FieldCustomization customization) {
        super(type, customization);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T<>($T.values())", EnumRandomizer.class, type);
    }

    @Override
    public ModelField create(FieldCustomization customization, ModelField... valueFields) {
        throw new UnsupportedOperationException("Create EnumField with constructor");
    }
}
