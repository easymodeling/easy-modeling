package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;

import java.util.Arrays;

public abstract class Container extends ModelField {

    protected ModelField[] nestedFields;

    protected Container() {
    }

    protected Container(TypeName type, FieldCustomization customization, ModelField... nestedFields) {
        super(type, customization);
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
}
