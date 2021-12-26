package xyz.v2my.easymodeling.factory.field;

import com.google.common.collect.Sets;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Randomizer;
import xyz.v2my.easymodeling.factory.Import;

import java.util.Set;

public abstract class PrimitiveField extends AbstractField {

    public PrimitiveField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    public String initializer() {
        return staticInitializer() + "()";
    }

    protected abstract String staticInitializer();

    @Override
    public Set<Import> imports() {
        return Sets.newHashSet(new Import(Randomizer.class, staticInitializer()));
    }
}
