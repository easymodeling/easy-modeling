package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.AbstractField;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.InstantRandomizer;

import java.time.Instant;

public class InstantField extends AbstractField {

    public InstantField() {
    }

    protected InstantField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public ModelField create(TypeName type, FieldWrapper field) {
        return new InstantField(type, field);
    }

    @Override
    public CodeBlock initializer() {
        if (field.now()) {
            return CodeBlock.of("$T.now()", Instant.class);
        }
        return CodeBlock.of("new $T($LL, $LL).next()", randomizer(), min(), max());
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
