package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.Container;
import io.github.easymodeling.modeler.field.ModelField;

public abstract class AbstractCollectionField extends Container {

    protected AbstractCollectionField() {
    }

    protected AbstractCollectionField(ClassName container, FieldCustomization customization, ModelField nestedField) {
        super(ParameterizedTypeName.get(container, nestedField.type()), customization, nestedField);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L, $L", minSize(), maxSize());
    }

    protected int maxSize() {
        return customization.maxSize().orElse(20);
    }

    private int minSize() {
        return customization.minSize().orElse(1);
    }
}
