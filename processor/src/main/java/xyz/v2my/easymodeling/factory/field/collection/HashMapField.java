package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.HashMapRandomizer;

import java.util.HashMap;

public class HashMapField extends AbstractMapField {

    public HashMapField(FieldWrapper field, ModelField keyField, ModelField valueField) {
        super(ClassName.get(HashMap.class), field, keyField, valueField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", HashMapRandomizer.class);
    }
}
