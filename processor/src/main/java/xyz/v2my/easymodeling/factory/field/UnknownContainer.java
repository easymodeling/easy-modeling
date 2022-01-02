package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import java.util.List;

public class UnknownContainer extends Container<Void> {

    public UnknownContainer() {
    }

    public UnknownContainer(TypeName type, FieldWrapper field, List<Type> valueFields) {
        super(type, field, valueFields);
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("null");
    }

    @Override
    public Container<?> create(TypeName type, FieldWrapper field, List<Type> nestedFields) {
        return new UnknownContainer(type, field, nestedFields);
    }

    @Override
    protected CodeBlock initializerType() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    protected CodeBlock randomParameter() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}
