package xyz.v2my.easymodeling.factory.field.array;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.array.ArrayRandomizer;

public class ArrayField extends Container {

    public ArrayField(TypeName type, FieldWrapper field, ModelField elementField) {
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
    public Container create(FieldWrapper field, ModelField... valueFields) {
        throw new UnsupportedOperationException("Create ArrayField with constructor");
    }
}
