package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.HashSetRandomizer;

import java.util.HashSet;
import java.util.List;

public class HashSetField extends AbstractCollectionField {

    public HashSetField(FieldWrapper field, List<ModelField> nestedFields) {
        super(ClassName.get(HashSet.class), field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", HashSetRandomizer.class);
    }
}
