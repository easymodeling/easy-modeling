package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.field.AbstractField;

public abstract class PrimitiveField extends AbstractField {

    public PrimitiveField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T().next()", randomizer());
    }
}
