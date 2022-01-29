package xyz.v2my.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.randomizer.Randomizer;

public abstract class PlainField<T> extends ModelField {

    protected PlainField() {
    }

    protected PlainField(TypeName type, FieldPattern field) {
        super(type, field);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T($L)", initializerType(), initializerParameter());
    }

    protected abstract Class<? extends Randomizer<T>> initializerType();

    protected abstract CodeBlock initializerParameter();

}
