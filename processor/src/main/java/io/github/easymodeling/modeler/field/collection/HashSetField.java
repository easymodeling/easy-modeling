package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.collection.HashSetRandomizer;

import java.util.HashSet;

public class HashSetField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(HashSet.class);

    public HashSetField() {
        this.type = TYPE;
    }

    @Override
    public HashSetField create(FieldCustomization field, ModelField... nestedFields) {
        return new HashSetField(field, nestedFields[0]);
    }

    private HashSetField(FieldCustomization field, ModelField nestedField) {
        super(TYPE, field, nestedField);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L", maxSize());
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", HashSetRandomizer.class);
    }
}
