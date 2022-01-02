package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.OptionalRandomizer;

import java.util.List;
import java.util.Optional;

public class OptionalType extends Container<Optional<?>> {

    public OptionalType() {
    }

    public OptionalType(TypeName type, FieldWrapper field, List<Type> valueFields) {
        super(type, field, valueFields);
    }

    @Override
    public Container<?> create(TypeName type, FieldWrapper field, List<Type> nestedFields) {
        return new OptionalType(type, field, nestedFields);
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
