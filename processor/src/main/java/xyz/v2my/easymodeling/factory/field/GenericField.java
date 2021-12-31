package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.Optional;

public class GenericField extends ModelField<Void> {

    private GenericField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public GenericField() {
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("null");
    }

    @Override
    public ModelField<Void> create(TypeName type, FieldWrapper field) {
        return new GenericField(type, field);
    }

    @Override
    protected Optional<CodeBlock> constantParameter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected CodeBlock randomParameter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Class<? extends Randomizer<Void>> initializerType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
