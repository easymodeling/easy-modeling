package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.collection.LinkedListRandomizer;

import java.util.LinkedList;

public class LinkedListField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(LinkedList.class);

    public LinkedListField() {
        this.type = TYPE;
    }

    @Override
    public LinkedListField create(FieldCustomization customization, ModelField... nestedFields) {
        return new LinkedListField(customization, nestedFields[0]);
    }

    private LinkedListField(FieldCustomization customization, ModelField nestedField) {
        super(TYPE, customization, nestedField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", LinkedListRandomizer.class);
    }
}
