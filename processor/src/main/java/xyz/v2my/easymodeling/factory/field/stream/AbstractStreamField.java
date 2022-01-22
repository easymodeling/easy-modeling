package xyz.v2my.easymodeling.factory.field.stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;

public abstract class AbstractStreamField extends Container {

    public AbstractStreamField() {
    }

    protected AbstractStreamField(ClassName stream, FieldWrapper field, ModelField nestedField) {
        super(ParameterizedTypeName.get(stream, nestedField.type()), field, nestedField);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L, $L", minSize(), maxSize());
    }

    private int maxSize() {
        return field.maxSize().orElse(20);
    }

    private int minSize() {
        return field.minSize().orElse(1);
    }

}
