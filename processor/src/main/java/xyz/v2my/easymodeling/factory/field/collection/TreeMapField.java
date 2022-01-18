package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.TreeMapRandomizer;

import java.util.TreeMap;

public class TreeMapField extends AbstractMapField {

    public TreeMapField(FieldWrapper field, ModelField keyField, ModelField valueField) {
        super(ClassName.get(TreeMap.class), field, keyField, valueField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", TreeMapRandomizer.class);
    }
}
