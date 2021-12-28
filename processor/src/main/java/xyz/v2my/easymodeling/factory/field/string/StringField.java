package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.factory.field.AbstractField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.StringRandomizer;

import java.util.Optional;

import static xyz.v2my.easymodeling.randomizer.StringRandomizer.ALPHABETIC;
import static xyz.v2my.easymodeling.randomizer.StringRandomizer.ALPHANUMERIC;
import static xyz.v2my.easymodeling.randomizer.StringRandomizer.NUMERIC;
import static xyz.v2my.easymodeling.randomizer.StringRandomizer.RANDOM;

public class StringField extends AbstractField {

    private final Field field;

    public StringField(TypeName type, String name, Field field) {
        super(type, name);
        this.field = field;
    }

    @Override
    public CodeBlock initializer() {
        final int charset = alphabetic() & numeric() & alphaNumeric();
        if (charset == 0) {
            // TODO: 28.12.21 switch to more specific exception
            throw new IllegalArgumentException("No charset specified");
        }
        return string().map(format -> CodeBlock.of("$S", format))
                .orElse(CodeBlock.of("new $T().next($L, $L, $L)", randomizer(), min(), max(), charset));
    }

    private long min() {
        return Optional.ofNullable(field).map(Field::min)
                .filter(min -> !min.isNaN())
                .map(Double::longValue)
                .filter(min -> min >= 1)
                .orElse(6L);
    }

    private long max() {
        return Optional.ofNullable(field).map(Field::max)
                .filter(min -> !min.isNaN())
                .map(Double::longValue)
                .filter(min -> min != Long.MAX_VALUE)
                .orElse(20L);
    }

    private int alphabetic() {
        return field != null && field.alphabetic() ? ALPHABETIC : RANDOM;
    }

    private int numeric() {
        return field != null && field.numeric() ? NUMERIC : RANDOM;
    }

    private int alphaNumeric() {
        return field == null || field.alphanumeric() ? ALPHANUMERIC : RANDOM;
    }

    private Optional<String> string() {
        return Optional.ofNullable(field).map(Field::string).filter(s -> !s.isEmpty());
    }

    @Override
    protected Class<? extends Randomizer> randomizer() {
        return StringRandomizer.class;
    }
}
