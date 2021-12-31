package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.Optional;

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

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T($L)", initializerType(), initializerParameter());
    }

    protected Class<? extends Randomizer<FIELD>> initializerType() {
        throw new UnsupportedOperationException("generic randomizer is not supported");
    }

    private CodeBlock initializerParameter() {
        return constantParameter().orElseGet(this::randomParameter);
    }

    protected abstract Optional<CodeBlock> constantParameter();

    protected CodeBlock randomParameter() {
        throw new UnsupportedOperationException("generic initializer is not supported");
    }

}
