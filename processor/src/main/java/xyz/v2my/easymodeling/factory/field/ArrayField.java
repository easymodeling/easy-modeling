package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.ArrayRandomizer;

import java.util.Collections;
import java.util.List;

public class ArrayField extends Container<Object> {

    private final ModelField elementField;

    public ArrayField(TypeName type, FieldWrapper field, ModelField elementField) {
        super(type, field, Collections.singletonList(elementField));
        this.elementField = elementField;
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $L($L, $L)", initializerType(), elementRandomizer(), randomParameter());
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("($L) new $L($L, $L).next()", type, initializerType(), elementRandomizer(), randomParameter());
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", ArrayRandomizer.class);
    }

    @Override
    protected CodeBlock randomParameter() {
        return CodeBlock.of("$T.class, $L, $L, $L", elementField.typeName(), dimension(), minLength(), maxLength());
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
    public Container<Object> create(TypeName type, FieldWrapper field, List<ModelField> nestedFields) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
