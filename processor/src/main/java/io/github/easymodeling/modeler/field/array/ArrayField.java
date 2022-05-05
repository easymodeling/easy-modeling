package io.github.easymodeling.modeler.field.array;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.Container;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.array.ArrayRandomizer;

public class ArrayField extends Container {

    public ArrayField(TypeName type, FieldCustomization field, ModelField elementField) {
        super(type, field, elementField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ArrayRandomizer.class);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L, $L", minSize(), maxSize());
    }

    private int maxSize() {
        return field.maxSize().orElse(20);
    }

    private int minSize() {
        return field.minSize().orElse(1);
    }

    @Override
    public Container create(FieldCustomization field, ModelField... valueFields) {
        throw new UnsupportedOperationException("Create ArrayField with constructor");
    }
}
