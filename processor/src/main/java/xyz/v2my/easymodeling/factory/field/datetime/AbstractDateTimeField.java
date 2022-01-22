package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;

import java.time.Instant;
import java.util.Optional;

public abstract class AbstractDateTimeField<T> extends PlainField<T> {

    protected AbstractDateTimeField() {
    }

    protected AbstractDateTimeField(TypeName type, FieldWrapper field) {
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
            return field.datetime().map(datetime -> CodeBlock.of("$T.ofEpochMilli($LL)", Instant.class, datetime.toEpochMilli()));
        }
    }

    protected CodeBlock randomParameter() {
        return CodeBlock.of("$LL, $LL", min(), max());
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
