package xyz.v2my.easymodeling.factory.field.stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.Container;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.stream.StreamRandomizer;

import java.util.stream.Stream;

public class StreamField extends Container {

    public static final ClassName TYPE = ClassName.get(Stream.class);

    public StreamField() {
        type = TYPE;
    }

    @Override
    public StreamField create(FieldWrapper field, ModelField... valueFields) {
        return new StreamField(field, valueFields[0]);
    }

    private StreamField(FieldWrapper field, ModelField nestedField) {
        super(ParameterizedTypeName.get(TYPE, nestedField.type()), field, nestedField);
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
        return field.maxSize().orElse(20);
    }

    private int minSize() {
        return field.minSize().orElse(1);
    }
}
