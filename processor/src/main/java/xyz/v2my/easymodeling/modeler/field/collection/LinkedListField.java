package xyz.v2my.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.collection.LinkedListRandomizer;

import java.util.LinkedList;

public class LinkedListField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(LinkedList.class);

    public LinkedListField() {
        this.type = TYPE;
    }

    @Override
    public LinkedListField create(FieldPattern field, ModelField... nestedFields) {
        return new LinkedListField(field, nestedFields[0]);
    }

    private LinkedListField(FieldPattern field, ModelField nestedField) {
        super(TYPE, field, nestedField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", LinkedListRandomizer.class);
    }
}
