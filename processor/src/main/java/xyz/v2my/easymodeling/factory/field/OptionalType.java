package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterizedTypeName;
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
    public Container<Optional<?>> create(TypeName type, FieldWrapper field, List<Type> nestedFields) {
        return new OptionalType(type, field, nestedFields);
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("$L.next()$L", initializer(), typeMapper());
    }

    private CodeBlock typeMapper() {
        final Type valueType = nestedFields.get(0);
        if (valueType instanceof ArrayType) {
            return CodeBlock.of(".map(o -> ($T) o)", valueType.type);
        } else {
            return CodeBlock.of("");
        }
    }

    @Override
    protected CodeBlock initializerType() {
        return CodeBlock.of("$T", OptionalRandomizer.class);
    }

    @Override
    protected CodeBlock randomParameter() {
        return CodeBlock.of("$L", field.allowEmpty());
    }

    @Override
    protected TypeName typeName() {
        return ((ParameterizedTypeName) type).rawType;
    }
}
