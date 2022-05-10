package io.github.easymodeling.modeler.field.collection;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.collection.SetRandomizer;

import java.util.Set;

public class SetField extends AbstractCollectionField {

    public static final ClassName TYPE = ClassName.get(Set.class);

    public SetField() {
        this.type = TYPE;
    }

    @Override
    public SetField create(FieldCustomization customization, ModelField... nestedFields) {
        return new SetField(customization, nestedFields[0]);
    }

    private SetField(FieldCustomization customization, ModelField nestedField) {
        super(TYPE, customization, nestedField);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L", maxSize());
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", SetRandomizer.class);
    }
}
