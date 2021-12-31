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
    protected Optional<CodeBlock> constantInitialization() {
        return Optional.empty();
    }

    @Override
    public CodeBlock initialization() {
        return CodeBlock.of("($L) $L.next()", type, initializer());
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T<>($L, $T.class, $L)", ArrayRandomizer.class, elementField.initializer(), elementField.type, dimension());
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
