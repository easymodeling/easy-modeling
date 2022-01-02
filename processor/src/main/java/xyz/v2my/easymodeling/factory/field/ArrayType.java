package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.ArrayRandomizer;

import java.util.Collections;
import java.util.List;

public class ArrayType extends Container<Object> {

    private final Type elementField;

    public ArrayType(TypeName type, FieldWrapper field, Type elementField) {
        super(type, field, Collections.singletonList(elementField));
        this.elementField = elementField;
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("($L) new $T($L).next()", type, ArrayRandomizer.class, randomParameter());
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T<>($L)", ArrayRandomizer.class, randomParameter());
    }

    @Override
    protected CodeBlock initializerType() {
        return null;
    }

    @Override
    protected CodeBlock randomParameter() {
        final Type elementField = this.elementField;
        return CodeBlock.of("$L, $T.class, $L, $L, $L", this.elementField.initializer(), elementField.typeName(), dimension(), minLength(), maxLength());
    }

    private int maxLength() {
        return field.maxLength().orElse(20);
    }

    private int minLength() {
        return field.minLength().orElse(1);
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
    public Container<?> create(TypeName type, FieldWrapper field, List<Type> nestedFields) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
