package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldPattern;

import java.util.Arrays;
import java.util.stream.Collectors;

public abstract class Container extends ModelField {

    protected ModelField[] nestedFields;

    protected Container() {
    }

    protected Container(TypeName type, FieldPattern field, ModelField... nestedFields) {
        super(type, field);
        this.nestedFields = nestedFields;
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

    @Override
    public String toString() {
        return String.format("%s<%s>", this.getClass().getSimpleName(), Arrays.stream(nestedFields).map(ModelField::toString).collect(Collectors.joining(", ")));
    }
}
