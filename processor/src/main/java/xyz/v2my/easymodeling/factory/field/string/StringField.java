package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.AbstractField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.StringRandomizer;

import java.util.Optional;

import static xyz.v2my.easymodeling.randomizer.StringRandomizer.ALPHABETIC;
import static xyz.v2my.easymodeling.randomizer.StringRandomizer.ALPHANUMERIC;
import static xyz.v2my.easymodeling.randomizer.StringRandomizer.NUMERIC;
import static xyz.v2my.easymodeling.randomizer.StringRandomizer.RANDOM;

public class StringField extends AbstractField {

    public StringField() {
    }

    protected StringField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public StringField create(TypeName type, FieldWrapper field) {
        return new StringField(type, field);
    }

    @Override
    public CodeBlock initializer() {
        final int charset = alphabetic() & numeric() & alphaNumeric();
        if (charset == 0) {
            // TODO: 28.12.21 switch to more specific exception
            throw new IllegalArgumentException("No charset specified");
        }
        return string().map(format -> CodeBlock.of("$S", format))
                .orElse(CodeBlock.of("new $T($L, $L, $L).next()", randomizer(), min(), max(), charset));
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

    @Override
    protected Class<? extends Randomizer<?>> randomizer() {
        return StringRandomizer.class;
    }
}
