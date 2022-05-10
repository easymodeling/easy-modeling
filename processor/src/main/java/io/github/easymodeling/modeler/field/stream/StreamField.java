package io.github.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.Container;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.stream.StreamRandomizer;

import java.util.stream.Stream;

public class StreamField extends Container {

    public static final ClassName TYPE = ClassName.get(Stream.class);

    public StreamField() {
        type = TYPE;
    }

    @Override
    public StreamField create(FieldCustomization customization, ModelField... valueFields) {
        return new StreamField(customization, valueFields[0]);
    }

    private StreamField(FieldCustomization customization, ModelField nestedField) {
        super(ParameterizedTypeName.get(TYPE, nestedField.type()), customization, nestedField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", StreamRandomizer.class);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L, $L", minSize(), maxSize());
    }

    private int maxSize() {
        return customization.maxSize().orElse(20);
    }

    private int minSize() {
        return customization.minSize().orElse(1);
    }
}
