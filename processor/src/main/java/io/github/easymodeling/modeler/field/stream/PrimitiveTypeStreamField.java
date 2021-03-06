package io.github.easymodeling.modeler.field.stream;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.modeler.field.number.NumericField;

import java.util.stream.BaseStream;

public abstract class PrimitiveTypeStreamField<S extends BaseStream<E, S>, E extends Number> extends PlainField<S> {

    protected PrimitiveTypeStreamField() {
    }

    protected PrimitiveTypeStreamField(TypeName type, FieldCustomization customization) {
        super(type, customization);
    }

    @Override
    protected CodeBlock initializerParameter() {
        final CodeBlock elementInitializer = element().create(customization).initializer();
        return CodeBlock.of("$L, $L, $L", elementInitializer, minSize(), maxSize());
    }

    protected abstract NumericField<E> element();

    private int maxSize() {
        return customization.maxSize().orElse(20);
    }

    private int minSize() {
        return customization.minSize().orElse(1);
    }
}
