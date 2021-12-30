package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import java.util.Optional;

import static xyz.v2my.easymodeling.randomizer.string.StringRandomizer.ALPHABETIC;
import static xyz.v2my.easymodeling.randomizer.string.StringRandomizer.ALPHANUMERIC;
import static xyz.v2my.easymodeling.randomizer.string.StringRandomizer.NUMERIC;
import static xyz.v2my.easymodeling.randomizer.string.StringRandomizer.RANDOM;

public abstract class CharSequenceField extends ModelField {

    public CharSequenceField() {
    }

    public CharSequenceField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public CodeBlock initializer() {
        final int charset = alphabetic() & numeric() & alphaNumeric();
        if (charset == 0) {
            // TODO: 28.12.21 switch to more specific exception
            throw new IllegalArgumentException("No charset specified");
        }
        return CodeBlock.of("new $T($L, $L, $L)", randomizer(), min(), max(), charset);
    }

    @Override
    protected Optional<CodeBlock> constantInitialization() {
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
        return field.alphabetic() ? ALPHABETIC : RANDOM;
    }

    private int numeric() {
        return field.numeric() ? NUMERIC : RANDOM;
    }

    private int alphaNumeric() {
        return field.alphanumeric() ? ALPHANUMERIC : RANDOM;
    }

    private Optional<String> string() {
        return field.string();
    }
}
