package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

public abstract class PlainField<T> extends ModelField {

    protected PlainField() {
    }

    protected PlainField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public abstract PlainField<T> create(TypeName type, FieldWrapper field);

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("$L.next()", initializer());
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T($L)", initializerType(), initializerParameter());
    }

    protected abstract Class<? extends Randomizer<T>> initializerType();

    protected abstract CodeBlock initializerParameter();

}