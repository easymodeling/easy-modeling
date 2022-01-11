package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.ArrayListRandomizer;

import java.util.ArrayList;
import java.util.List;

public class ArrayListField extends AbstractCollectionField {

    public ArrayListField(FieldWrapper field, List<ModelField> nestedFields) {
        super(ClassName.get(ArrayList.class), field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ArrayListRandomizer.class);
    }
}
