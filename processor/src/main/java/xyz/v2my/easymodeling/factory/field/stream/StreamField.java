package xyz.v2my.easymodeling.factory.field.stream;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.stream.StreamRandomizer;

import java.util.stream.Stream;

public class StreamField extends AbstractStreamField {

    public static final ClassName TYPE = ClassName.get(Stream.class);

    public StreamField() {
        type = TYPE;
    }

    @Override
    public StreamField create(FieldWrapper field, ModelField... valueFields) {
        return new StreamField(field, valueFields[0]);
    }

    private StreamField(FieldWrapper field, ModelField nestedField) {
        super(TYPE, field, nestedField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", StreamRandomizer.class);
    }
}
