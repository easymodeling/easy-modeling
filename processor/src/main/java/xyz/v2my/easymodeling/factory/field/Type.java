package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class Type extends BuilderField implements InitializableType {

    public Type() {
    }

    public Type(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("$L.next()", initializer());
    }

    protected TypeName typeName() {
        return type;
    }
}
