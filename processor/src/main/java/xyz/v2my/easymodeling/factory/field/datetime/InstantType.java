package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainType;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.InstantRandomizer;

import java.time.Instant;
import java.util.Optional;

public class InstantType extends PlainType<Instant> {

    public InstantType() {
    }

    private InstantType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return constantParameter().orElseGet(this::randomParameter);
    }

    protected Optional<CodeBlock> constantParameter() {
        if (field.now()) {
            return Optional.of(CodeBlock.of("$T.now()", Instant.class));
        } else {
            return field.datetime().map(datetime -> CodeBlock.of("new $T($T.ofEpochMilli($L))", initializerType(), Instant.class, datetime.toEpochMilli()));
        }
    }

    @Override
    public PlainType<Instant> create(TypeName type, FieldWrapper field) {
        return new InstantType(type, field);
    }

    protected CodeBlock randomParameter() {
        return CodeBlock.of("$LL, $LL", min(), max());
    }

    @Override
    protected Class<? extends Randomizer<Instant>> initializerType() {
        return InstantRandomizer.class;
    }

    private long min() {
        return field.after().map(Instant::toEpochMilli).orElse(0L);
    }

    private long max() {
        return field.before().map(Instant::toEpochMilli).orElse(ceiling());
    }

    private long ceiling() {
        return 1_000L * Integer.MAX_VALUE;
    }

}
