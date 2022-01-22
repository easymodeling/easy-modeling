package xyz.v2my.easymodeling.factory.field.stream;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.factory.field.number.NumericField;

import java.util.stream.BaseStream;

public abstract class PrimitiveTypeStreamField<S extends BaseStream<E, S>, E extends Number> extends PlainField<S> {

    protected PrimitiveTypeStreamField() {
    }

    public PrimitiveTypeStreamField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    protected CodeBlock initializerParameter() {
        final CodeBlock elementInitializer = element().create(field).initializer();
        return CodeBlock.of("$L, $L, $L", elementInitializer, minSize(), maxSize());
    }

    protected abstract NumericField<E> element();

    private int maxSize() {
        return field.maxSize().orElse(20);
    }

    private int minSize() {
        return field.minSize().orElse(1);
    }
}
