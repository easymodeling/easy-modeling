package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

public class UnknownField extends PlainField<Void> {

    public UnknownField() {
    }

    private UnknownField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public PlainField<Void> create(TypeName type, FieldWrapper field) {
        return new UnknownField(type, field);
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("null");
    }

    @Override
    public CodeBlock initializer() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected Class<? extends Randomizer<Void>> initializerType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected CodeBlock initializerParameter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
