package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.OptionalRandomizer;

import java.util.List;

public class OptionalField extends Container {

    public OptionalField() {
    }

    private OptionalField(TypeName type, FieldWrapper field, List<ModelField> valueFields) {
        super(type, field, valueFields);
    }

    @Override
    public OptionalField create(TypeName type, FieldWrapper field, List<ModelField> nestedFields) {
        return new OptionalField(type, field, nestedFields);
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
