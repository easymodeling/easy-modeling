package xyz.v2my.easymodeling.factory.field.primitive;

import com.google.common.collect.Sets;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.Import;
import xyz.v2my.easymodeling.factory.field.AbstractField;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import java.util.Set;

public abstract class PrimitiveField extends AbstractField {

    public PrimitiveField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("$L()", staticInitializer());
    }

    protected abstract String staticInitializer();

    @Override
    public Set<Import> imports() {
        return Sets.newHashSet(new Import(Randomizer.class, staticInitializer()));
    }
}
