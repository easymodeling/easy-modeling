package io.github.easymodeling.modeler.field.array;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.Container;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.array.PrimitiveArrayRandomizer;

public class PrimitiveArrayField extends Container {

    public PrimitiveArrayField(ArrayTypeName type, FieldCustomization customization, ModelField elementField) {
        super(type, customization, elementField);
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("($L) $L.next()", type, initializer());
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $L($L, $L)", initializerType(), nestedRandomizers(), initializerParameter());
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", PrimitiveArrayRandomizer.class);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L, $L, $L", dimension(), minSize(), maxSize());
    }

    private int maxSize() {
        return customization.maxSize().orElse(20);
    }

    private int minSize() {
        return customization.minSize().orElse(1);
    }

    private int dimension() {
        return dimensionOf(type);
    }

    private int dimensionOf(TypeName type) {
        if (type instanceof ArrayTypeName) {
            return 1 + dimensionOf(((ArrayTypeName) type).componentType);
        }
        return 0;
    }

    @Override
    public Container create(FieldCustomization customization, ModelField... valueFields) {
        throw new UnsupportedOperationException("Create primitive array with constructor");
    }
}
