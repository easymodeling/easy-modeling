package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldPattern;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.HashMapRandomizer;

import java.util.HashMap;

public class HashMapField extends AbstractMapField {

    public static final ClassName TYPE = ClassName.get(HashMap.class);

    public HashMapField() {
        this.type = TYPE;
    }

    @Override
    public HashMapField create(FieldPattern field, ModelField... nestedFields) {
        return new HashMapField(field, nestedFields[0], nestedFields[1]);
    }

    private HashMapField(FieldPattern field, ModelField keyField, ModelField valueField) {
        super(TYPE, field, keyField, valueField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", HashMapRandomizer.class);
    }
}
