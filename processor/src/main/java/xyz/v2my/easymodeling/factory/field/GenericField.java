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

    @Override
    protected Optional<CodeBlock> constantInitialization() {
        return Optional.empty();
    }

    @Override
    public CodeBlock initialization() {
        return CodeBlock.of("null");
    }

    public GenericField() {
    }

    @Override
    protected Class<? extends Randomizer<Void>> randomizer() {
        return null;
    }

    @Override
    public ModelField<Void> create(TypeName type, FieldWrapper field) {
        return new GenericField(type, field);
    }

}
