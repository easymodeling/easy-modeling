package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.collection.ArrayListRandomizer;

import java.util.ArrayList;

public class ArrayListField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(ArrayList.class);

    public ArrayListField() {
        this.type = TYPE;
    }

    @Override
    public ArrayListField create(FieldCustomization customization, ModelField... nestedFields) {
        return new ArrayListField(customization, nestedFields[0]);
    }

    private ArrayListField(FieldCustomization customization, ModelField nestedField) {
        super(TYPE, customization, nestedField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ArrayListRandomizer.class);
    }
}
