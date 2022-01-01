package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

public abstract class InitializableField<FIELD> extends BuilderField implements Initializable {

    public InitializableField() {
    }

    protected InitializableField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("$L.next()", initializer());
    }

    public CodeBlock initializer() {
        return CodeBlock.of("new $T($L)", initializerType(), initializerParameter());
    }

    protected abstract Class<? extends Randomizer<FIELD>> initializerType();

    protected abstract CodeBlock initializerParameter();

}
