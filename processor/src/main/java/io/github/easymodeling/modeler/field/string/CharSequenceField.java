package io.github.easymodeling.modeler.field.string;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.PlainField;

import java.util.Optional;

import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.ALPHABETIC;
import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.ALPHANUMERIC;
import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.ANY;
import static io.github.easymodeling.randomizer.string.CharSequenceRandomizer.NUMERIC;

public abstract class CharSequenceField<T extends CharSequence> extends PlainField<T> {

    protected CharSequenceField() {
    }

    protected CharSequenceField(TypeName type, FieldCustomization customization) {
        super(type, customization);
    }

    @Override
    protected CodeBlock initializerParameter() {
        return constantParameter().orElseGet(this::randomParameter);
    }

    public CodeBlock randomParameter() {
        final int charRange = alphabetic() & numeric() & alphaNumeric();
        if (charRange == 0) {
            throw new IllegalArgumentException("No char range specified");
        }
        return CodeBlock.of("$L, $L, $L", min(), max(), charRange);
    }

    protected Optional<CodeBlock> constantParameter() {
        return string().map(s -> CodeBlock.of("$S", s));
    }

    private long min() {
        return customization.min()
                .map(Double::longValue)
                .filter(min -> min >= 1)
                .orElse(6L);
    }

    private long max() {
        return customization.max()
                .map(Double::longValue)
                .filter(max -> max < Integer.MAX_VALUE)
                .orElse(20L);
    }

    private int alphabetic() {
        return customization.alphabetic() ? ALPHABETIC : ANY;
    }

    private int numeric() {
        return customization.numeric() ? NUMERIC : ANY;
    }

    private int alphaNumeric() {
        return customization.alphanumeric() ? ALPHANUMERIC : ANY;
    }

    private Optional<String> string() {
        return customization.string();
    }
}
