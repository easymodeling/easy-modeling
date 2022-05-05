package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.collection.TreeSetRandomizer;

import java.util.TreeSet;

public class TreeSetField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(TreeSet.class);

    public TreeSetField() {
        this.type = TYPE;
    }

    @Override
    public TreeSetField create(FieldCustomization field, ModelField... nestedFields) {
        return new TreeSetField(field, nestedFields[0]);
    }

    private TreeSetField(FieldCustomization field, ModelField nestedField) {
        super(TYPE, field, nestedField);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L", maxSize());
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", TreeSetRandomizer.class);
    }
}
