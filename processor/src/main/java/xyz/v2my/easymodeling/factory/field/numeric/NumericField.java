package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.factory.field.PrimitiveField;

import java.util.Optional;

public abstract class NumericField extends PrimitiveField {

    protected final Field field;

    public NumericField(TypeName type, String name, Field field) {
        super(type, name);
        this.field = field;
    }

    @Override
    public String initializer() {
        return String.format("%s(%sL, %sL)", staticInitializer(), min(), max());
    }

    protected long min() {
        return Optional.ofNullable(field).map(Field::min).map(bound -> Math.max(bound, floor())).orElse(0L);
    }

    protected long max() {
        return Optional.ofNullable(field).map(Field::max).map(bound -> Math.min(bound, ceiling())).orElse(ceiling());
    }

    protected abstract long ceiling();

    protected abstract long floor();

}
