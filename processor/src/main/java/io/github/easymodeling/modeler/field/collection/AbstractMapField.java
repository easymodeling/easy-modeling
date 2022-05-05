package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.Container;
import io.github.easymodeling.modeler.field.ModelField;

public abstract class AbstractMapField extends Container {

    protected AbstractMapField() {
    }

    protected AbstractMapField(ClassName container, FieldCustomization field, ModelField keyField, ModelField valueField) {
        super(ParameterizedTypeName.get(container, keyField.type(), valueField.type()), field, keyField, valueField);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L", maxSize());
    }

    private int maxSize() {
        return field.maxSize().orElse(20);
    }
}
