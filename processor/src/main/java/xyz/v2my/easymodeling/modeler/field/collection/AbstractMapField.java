package xyz.v2my.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.Container;
import xyz.v2my.easymodeling.modeler.field.ModelField;

public abstract class AbstractMapField extends Container {

    protected AbstractMapField() {
    }

    protected AbstractMapField(ClassName container, FieldPattern field, ModelField keyField, ModelField valueField) {
        super(ParameterizedTypeName.get(container, keyField.type(), valueField.type()), field, keyField, valueField);
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