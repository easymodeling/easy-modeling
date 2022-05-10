package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.PlainField;

import java.time.Instant;
import java.util.Optional;

public abstract class AbstractDateTimeField<T> extends PlainField<T> {

    protected AbstractDateTimeField() {
    }

    protected AbstractDateTimeField(TypeName type, FieldCustomization customization) {
        super(type, customization);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return constantParameter().orElseGet(this::randomParameter);
    }

    protected Optional<CodeBlock> constantParameter() {
        if (customization.now()) {
            return Optional.of(CodeBlock.of("$T.now()", Instant.class));
        } else {
            return customization.datetime().map(datetime -> CodeBlock.of("$T.ofEpochMilli($LL)", Instant.class, datetime.toEpochMilli()));
        }
    }

    protected CodeBlock randomParameter() {
        return CodeBlock.of("$LL, $LL", min(), max());
    }

    private long min() {
        return customization.after().map(Instant::toEpochMilli).orElse(0L);
    }

    private long max() {
        return customization.before().map(Instant::toEpochMilli).orElse(ceiling());
    }

    private long ceiling() {
        return 1_000L * Integer.MAX_VALUE;
    }

}
