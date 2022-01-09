package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;

import java.util.List;

public abstract class AbstractCollectionField extends Container {

    public AbstractCollectionField(ParameterizedTypeName typeName, FieldWrapper field, List<ModelField> nestedFields) {
        super(typeName, field, nestedFields);
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
