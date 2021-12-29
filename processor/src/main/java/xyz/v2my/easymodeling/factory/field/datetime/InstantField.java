package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.InstantRandomizer;

import java.time.Instant;
import java.util.Optional;

public class InstantField extends ModelField {

    public InstantField() {
    }

    protected InstantField(TypeName type, FieldWrapper field) {
        this.type = type;
        this.name = field.name();
        this.field = field;
    }

    @Override
    protected Optional<CodeBlock> constantInitialization() {
        if (field.now()) {
            return Optional.of(CodeBlock.of("$T.now()", Instant.class));
        } else {
            return field.datetime().map(datetime -> CodeBlock.of("$T.ofEpochMilli($L)", Instant.class, datetime.toEpochMilli()));
        }
    }

    @Override
    public ModelField create(TypeName type, FieldWrapper field) {
        return new InstantField(type, field);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T($LL, $LL)", randomizer(), min(), max());
    }

    private long min() {
        return field.after().map(Instant::toEpochMilli).orElse(floor());
    }

    private long max() {
        return field.before().map(Instant::toEpochMilli).orElse(ceiling());
    }

    protected long ceiling() {
        return 1_000L * Integer.MAX_VALUE;
    }

    protected long floor() {
        return 0;
    }

    @Override
    protected Class<? extends Randomizer<?>> randomizer() {
        return InstantRandomizer.class;
    }
}
