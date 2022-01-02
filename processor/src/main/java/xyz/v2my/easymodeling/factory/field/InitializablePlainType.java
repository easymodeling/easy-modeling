package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

public abstract class InitializablePlainType<FIELD> extends Type {

    public InitializablePlainType() {
    }

    protected InitializablePlainType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T($L)", initializerType(), initializerParameter());
    }

    protected abstract Class<? extends Randomizer<FIELD>> initializerType();

    protected abstract CodeBlock initializerParameter();

}
