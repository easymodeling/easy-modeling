package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainType;

public abstract class PrimitiveType<E> extends PlainType<E> {

    protected PrimitiveType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public PrimitiveType() {
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("");
    }
}
