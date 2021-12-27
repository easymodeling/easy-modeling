package xyz.v2my.easymodeling.factory.field.numeric;

import com.google.common.collect.Sets;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.factory.Import;
import xyz.v2my.easymodeling.factory.field.AbstractField;
import xyz.v2my.easymodeling.randomizer.NumericRandomizer;

import java.util.Optional;
import java.util.Set;

public abstract class NumericField extends AbstractField {

    protected final Field field;

    public NumericField(TypeName type, String name, Field field) {
        super(type, name);
        this.field = field;
    }

    @Override
    public CodeBlock initializer() {
        return constantInit().orElseGet(this::randomInit);
    }

    protected long min() {
        return Optional.ofNullable(field).map(Field::min).map(bound -> Math.max(bound, floor())).orElse(0L);
    }

    protected long max() {
        return Optional.ofNullable(field).map(Field::max).map(bound -> Math.min(bound, ceiling())).orElse(ceiling());
    }

    private Optional<CodeBlock> constantInit() {
        return Optional.ofNullable(field).map(Field::constant)
                .filter(d -> !d.isNaN())
                .filter(d -> d <= ceiling() && d >= floor())
                .map(this::constantInit);
    }

    private CodeBlock randomInit() {
        return CodeBlock.of("$L($LL, $LL)", staticInitializer(), min(), max());
    }

    @Override
    public Set<Import> imports() {
        return Sets.newHashSet(new Import(NumericRandomizer.class, staticInitializer()));
    }

    protected abstract String staticInitializer();

    protected abstract long ceiling();

    protected abstract long floor();

    protected abstract CodeBlock constantInit(Double c);
}
