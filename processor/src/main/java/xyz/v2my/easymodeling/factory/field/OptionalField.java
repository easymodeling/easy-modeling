package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.OptionalRandomizer;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.Optional;

public class OptionalField<VALUE> extends PlainField<Optional<VALUE>> {

    private PlainField<VALUE> valueField;

    public OptionalField() {
    }

    public OptionalField(TypeName type, FieldWrapper field, PlainField<VALUE> valueField) {
        super(type, field);
        this.valueField = valueField;
    }

    @Override
    protected Class<? extends Randomizer<Optional<VALUE>>> initializerType() {
        // TODO: 01.01.22 extract to abstract non-creatable class
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected CodeBlock initializerParameter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T<>($L)", OptionalRandomizer.class, randomParameter());
    }

    protected CodeBlock randomParameter() {
        return CodeBlock.of("$L, $L", elementRandomizer(), allowEmpty());
    }

    private boolean allowEmpty() {
        return field.allowEmpty();
    }

    private CodeBlock elementRandomizer() {
        return CodeBlock.of("new $T($L)", valueField.initializerType(), valueField.initializerParameter());
    }

    @Override
    public PlainField<Optional<VALUE>> create(TypeName type, FieldWrapper field) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
