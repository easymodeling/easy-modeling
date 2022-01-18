package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.MapRandomizer;

import java.util.Map;

public class MapField extends AbstractMapField {

    public MapField(FieldWrapper field, ModelField keyField, ModelField valueField) {
        super(ClassName.get(Map.class), field, keyField, valueField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", MapRandomizer.class);
    }
}
