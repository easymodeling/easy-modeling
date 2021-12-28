package xyz.v2my.easymodeling.factory.field.string;

import com.google.common.collect.Sets;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.factory.Import;
import xyz.v2my.easymodeling.factory.field.AbstractField;
import xyz.v2my.easymodeling.randomizer.StringRandomizer;

import java.util.Optional;
import java.util.Set;

public class StringField extends AbstractField {

    private final Field field;

    public StringField(TypeName type, String name, Field field) {
        super(type, name);
        this.field = field;
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("aString($L, $L)", min(), max());
    }

    private long min() {
        return Optional.ofNullable(field).map(Field::min).filter(min -> min != 0).orElse(6L);
    }

    private long max() {
        return Optional.ofNullable(field).map(Field::max).filter(min -> min != Long.MAX_VALUE).orElse(20L);
    }

    @Override
    public Set<Import> imports() {
        return Sets.newHashSet(new Import(StringRandomizer.class, "aString"));
    }
}
