package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.collection.ListRandomizer;

import java.util.List;

public class ListField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(List.class);

    public ListField() {
        this.type = TYPE;
    }

    @Override
    public ListField create(FieldCustomization field, ModelField... nestedFields) {
        return new ListField(field, nestedFields[0]);
    }

    private ListField(FieldCustomization field, ModelField nestedFields) {
        super(TYPE, field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ListRandomizer.class);
    }
}
