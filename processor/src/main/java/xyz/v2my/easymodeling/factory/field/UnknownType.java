package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

public class UnknownType extends PlainType<Void> {

    public UnknownType() {
    }

    private UnknownType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public PlainType<Void> create(TypeName type, FieldWrapper field) {
        return new UnknownType(type, field);
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
