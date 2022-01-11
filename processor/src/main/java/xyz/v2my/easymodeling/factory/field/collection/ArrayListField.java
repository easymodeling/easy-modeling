package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.ArrayListRandomizer;

import java.util.ArrayList;

public class ArrayListField extends AbstractCollectionField {

    public ArrayListField(FieldWrapper field, ModelField nestedField) {
        super(ClassName.get(ArrayList.class), field, nestedField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ArrayListRandomizer.class);
    }
}
