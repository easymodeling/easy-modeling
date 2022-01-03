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
    public CodeBlock initialValue() {
        return CodeBlock.of("$L.next()$L", initializer(), typeMapper());
    }

    private CodeBlock typeMapper() {
        final ModelField valueField = nestedFields.get(0);
        if (valueField instanceof OptionalField) {
            return CodeBlock.of(".map(_$L -> _$L$L)", valueField.identifier, valueField.identifier, ((OptionalField) valueField).typeMapper());
        }
        if (valueField instanceof ArrayField) {
            return CodeBlock.of(".map(_$L -> ($T) _$L)", valueField.identifier, valueField.type, valueField.identifier);
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
}
