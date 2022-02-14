package io.github.easymodeling.modeler.field.string;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.PlainField;

import java.util.Optional;

import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.ALPHABETIC;
import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.ALPHANUMERIC;
import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.ANY;
import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.NUMERIC;

public abstract class CharSequenceField<T extends CharSequence> extends PlainField<T> {

    protected CharSequenceField() {
    }

    protected CharSequenceField(TypeName type, FieldPattern field) {
        super(type, field);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return constantParameter().orElseGet(this::randomParameter);
    }

    public CodeBlock randomParameter() {
        final int charset = alphabetic() & numeric() & alphaNumeric();
        if (charset == 0) {
            // TODO: 28.12.21 switch to more specific exception
            throw new IllegalArgumentException("No charset specified");
        }
        return CodeBlock.of("$L, $L, $L", min(), max(), charset);
    }

    protected Optional<CodeBlock> constantParameter() {
        return string().map(s -> CodeBlock.of("$S", s));
    }

    private long min() {
        return field.min()
                .map(Double::longValue)
                .filter(min -> min >= 1)
                .orElse(6L);
    }

    private long max() {
        // FIXME: 28.12.21 should limit the reasonable string length in validation
        return field.max()
                .map(Double::longValue)
                .filter(min -> min < Integer.MAX_VALUE)
                .orElse(20L);
    }

    private int alphabetic() {
        return field.alphabetic() ? ALPHABETIC : ANY;
    }

    private int numeric() {
        return field.numeric() ? NUMERIC : ANY;
    }

    private int alphaNumeric() {
        return field.alphanumeric() ? ALPHANUMERIC : ANY;
    }

    private Optional<String> string() {
        return field.string();
    }
}
