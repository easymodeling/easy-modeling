package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.ArrayRandomizer;

import java.util.Optional;

public class ArrayField extends ModelField<Object> {

    private final ModelField<?> elementField;

    public ArrayField(TypeName type, FieldWrapper field, ModelField<?> elementField) {
        super(type, field);
        this.elementField = elementField;
    }

    @Override
    public ModelField<Object> create(TypeName type, FieldWrapper field) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Optional<CodeBlock> constantParameter() {
        return Optional.empty();
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("($L) $L.next()", type, randomParameter());
    }

    @Override
    public CodeBlock randomParameter() {
        return CodeBlock.of("new $T<>(new $T($L), $T.class, $L, $L, $L)", ArrayRandomizer.class,
                elementField.initializerType(), elementField.randomParameter(), elementField.type, dimension(), min(), max());
    }

    private int max() {
        return field.size().orElse(fieldMax());
    }

    private int fieldMax() {
        return field.max().map(Double::intValue).orElse(20);
    }

    private int min() {
        return field.size().orElse(fieldMin());
    }

    private int fieldMin() {
        return field.min().map(Double::intValue).filter(min -> min >= 0).orElse(1);
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
}
