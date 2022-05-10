package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;

public class UnknownField extends ModelField {

    public UnknownField(TypeName type, FieldCustomization customization) {
        super(type, customization);
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock initializer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public PlainField<Void> create(FieldCustomization customization, ModelField... valueFields) {
        throw new UnsupportedOperationException("Create UnknownField with constructor");
    }
}
