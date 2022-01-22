package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import java.util.Arrays;

public abstract class Container extends ModelField {

    protected ModelField[] nestedFields;

    protected Container() {
    }

    protected Container(TypeName type, FieldWrapper field, ModelField... nestedFields) {
        super(type, field);
        this.nestedFields = nestedFields;
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("$L.next()", initializer());
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $L<>($L, $L)", initializerType(), nestedRandomizers(), initializerParameter());
    }

    protected CodeBlock nestedRandomizers() {
        return Arrays.stream(nestedFields)
                .map(ModelField::initializer)
                .map(init -> CodeBlock.of("$L", init))
                .collect(CodeBlock.joining(", "));
    }

    protected abstract CodeBlock initializerType();

    protected abstract CodeBlock initializerParameter();

}
