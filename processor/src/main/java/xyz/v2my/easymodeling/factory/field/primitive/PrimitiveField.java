package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;

import java.util.Optional;

public abstract class PrimitiveField<E> extends ModelField<E> {

    protected PrimitiveField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public PrimitiveField() {
    }

    @Override
    protected Optional<CodeBlock> constantParameter() {
        return Optional.empty();
    }

    @Override
    protected CodeBlock randomParameter() {
        return CodeBlock.of("");
    }
}
