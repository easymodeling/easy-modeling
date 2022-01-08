package xyz.v2my.easymodeling.factory.field.array;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.array.PrimitiveArrayRandomizer;

import java.util.Collections;
import java.util.List;

public class PrimitiveArrayField extends Container {

    public PrimitiveArrayField(ArrayTypeName type, FieldWrapper field, ModelField elementField) {
        super(type, field, Collections.singletonList(elementField));
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
        return field.maxSize().orElse(20);
    }

    private int minSize() {
        return field.minSize().orElse(1);
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
    public Container create(TypeName type, FieldWrapper field, List<ModelField> nestedFields) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
