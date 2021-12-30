package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.Optional;

public abstract class InitializableField extends BuilderField implements Initializable {

    public InitializableField() {
    }

    protected InitializableField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public CodeBlock initialization() {
        return constantInitialization().orElse(randomInitialization());
    }

    private CodeBlock randomInitialization() {
        return CodeBlock.of("$L.next()", initializer());
    }

    protected abstract Optional<CodeBlock> constantInitialization();

    protected CodeBlock initializer() {
        throw new UnsupportedOperationException("generic initializer is not supported");
    }

    protected Class<? extends Randomizer<?>> randomizer() {
        throw new UnsupportedOperationException("generic randomizer is not supported");
    }

}
