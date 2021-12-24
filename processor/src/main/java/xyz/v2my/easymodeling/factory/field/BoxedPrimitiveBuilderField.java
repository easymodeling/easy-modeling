package xyz.v2my.easymodeling.factory.field;

import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Randomizer;
import xyz.v2my.easymodeling.factory.Import;

import java.util.Set;

public class BoxedPrimitiveBuilderField extends AbstractBuilderField {

    private final String staticInitializer;

    public BoxedPrimitiveBuilderField(TypeName type, String name) {
        super(type, name);
        this.staticInitializer = init();
    }

    public String init() {
        if (type.equals(ClassName.get(Boolean.class))) {
            return "aBoolean";
        } else if (type.equals(ClassName.get(Byte.class))) {
            return "aByte";
        } else if (type.equals(ClassName.get(Short.class))) {
            return "aShort";
        } else if (type.equals(ClassName.get(Integer.class))) {
            return "anInt";
        } else if (type.equals(ClassName.get(Long.class))) {
            return "aLong";
        } else if (type.equals(ClassName.get(Float.class))) {
            return "aFloat";
        } else if (type.equals(ClassName.get(Double.class))) {
            return "aDouble";
        } else if (type.equals(ClassName.get(Character.class))) {
            return "aChar";
        } else {
            throw new IllegalArgumentException("Boxed Primitive " + type + " not supported");
        }
    }

    @Override
    public String initializer() {
        return staticInitializer + "()";
    }

    @Override
    public Set<Import> imports() {
        return Sets.newHashSet(new Import(Randomizer.class, staticInitializer));
    }
}
