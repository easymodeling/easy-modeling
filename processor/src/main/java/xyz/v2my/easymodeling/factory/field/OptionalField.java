package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
import xyz.v2my.easymodeling.factory.FieldPattern;
import xyz.v2my.easymodeling.randomizer.OptionalRandomizer;

import java.util.Optional;

public class OptionalField extends Container {

    public static final ClassName TYPE = ClassName.get(Optional.class);

    public OptionalField() {
        this.type = TYPE;
    }

    @Override
    public OptionalField create(FieldPattern field, ModelField... valueFields) {
        return new OptionalField(field, valueFields[0]);
    }

    private OptionalField(FieldPattern field, ModelField valueField) {
        super(ParameterizedTypeName.get(TYPE, valueField.type()), field, valueField);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", OptionalRandomizer.class);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return CodeBlock.of("$L", field.allowEmpty());
    }
}
