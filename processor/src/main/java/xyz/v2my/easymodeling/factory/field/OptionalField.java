package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.OptionalRandomizer;

import java.util.List;
import java.util.Optional;

public class OptionalField extends ContainerField<Optional<?>> {

    public OptionalField() {
    }

    public OptionalField(TypeName type, FieldWrapper field, List<ModelField> valueFields) {
        super(type, field, valueFields);
    }

    @Override
    public ContainerField<?> create(TypeName type, FieldWrapper field, List<ModelField> nestedFields) {
        return new OptionalField(type, field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", OptionalRandomizer.class);
    }

    @Override
    protected CodeBlock randomParameter() {
        return CodeBlock.of("$L", allowEmpty());
    }

    private boolean allowEmpty() {
        return field.allowEmpty();
    }
}
