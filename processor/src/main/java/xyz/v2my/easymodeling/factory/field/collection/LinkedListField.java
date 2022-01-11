package xyz.v2my.easymodeling.factory.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.LinkedListRandomizer;

import java.util.LinkedList;
import java.util.List;

public class LinkedListField extends AbstractCollectionField {

    public LinkedListField(FieldWrapper field, List<ModelField> nestedFields) {
        super(ClassName.get(LinkedList.class), field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", LinkedListRandomizer.class);
    }
}
