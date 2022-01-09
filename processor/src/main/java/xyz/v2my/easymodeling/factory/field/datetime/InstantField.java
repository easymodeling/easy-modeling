package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.InstantRandomizer;

import java.time.Instant;
import java.util.Optional;

public class InstantField extends PlainField<Instant> {

    public InstantField(FieldWrapper field) {
        super(ClassName.get(Instant.class), field);
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
