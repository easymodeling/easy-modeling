package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.ArrayRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

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
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("($L) new $T<>($L)", type, ArrayRandomizer.class, randomParameter());
    }

    @Override
    protected Class<? extends Randomizer<Object>> initializerType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodeBlock randomParameter() {
        return CodeBlock.of("$L, $T.class, $L, $L, $L", elementRandomizer(), elementField.type, dimension(), min(), max());
    }

    private CodeBlock elementRandomizer() {
        return CodeBlock.of("new $T($L)", elementField.initializerType(), elementField.initializerParameter());
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
