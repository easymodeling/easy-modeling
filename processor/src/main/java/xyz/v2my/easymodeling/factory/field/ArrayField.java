package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.ArrayRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

public class ArrayField extends ModelField<Object> {

    private final ModelField<?> elementField;

    public ArrayField(TypeName type, FieldWrapper field, ModelField<?> elementField) {
        super(type, field);
        this.elementField = elementField;
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("($L) new $T<>($L)", type, ArrayRandomizer.class, randomParameter());
    }

    public CodeBlock randomParameter() {
        return CodeBlock.of("$L, $T.class, $L, $L, $L", elementRandomizer(), elementField.type, dimension(), minLength(), maxLength());
    }

    private CodeBlock elementRandomizer() {
        return CodeBlock.of("new $T($L)", elementField.initializerType(), elementField.initializerParameter());
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
    public ModelField<Object> create(TypeName type, FieldWrapper field) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Class<? extends Randomizer<Object>> initializerType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected CodeBlock initializerParameter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
