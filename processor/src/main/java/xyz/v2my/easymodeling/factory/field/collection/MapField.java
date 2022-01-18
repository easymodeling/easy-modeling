package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.MapRandomizer;

import java.util.Map;

public class MapField extends Container {

    public MapField(FieldWrapper field, ModelField keyField, ModelField valueField) {
        super(ParameterizedTypeName.get(ClassName.get(Map.class), keyField.type(), valueField.type()), field, keyField, valueField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", MapRandomizer.class);
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
