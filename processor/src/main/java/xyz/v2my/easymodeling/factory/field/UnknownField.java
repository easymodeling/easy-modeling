package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldPattern;

public class UnknownField extends ModelField {

    public UnknownField(TypeName type, FieldPattern field) {
        super(type, field);
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
    public PlainField<Void> create(FieldPattern field, ModelField... valueFields) {
        throw new UnsupportedOperationException("Create UnknownField with constructor");
    }
}
