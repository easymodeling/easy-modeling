package xyz.v2my.easymodeling.factory.field;

import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.Randomizer;
import xyz.v2my.easymodeling.factory.Import;

import java.util.Set;

public class PrimitiveField extends AbstractField {

    private final String staticInitializer;

    public PrimitiveField(Field fieldDeclaration, TypeName type, String name) {
        super(type, name);
        this.staticInitializer = init();
    }

    public String init() {
        if (type.equals(ClassName.get(Boolean.class)) || type.equals(ClassName.BOOLEAN)) {
            return "aBoolean";
        } else if (type.equals(ClassName.get(Byte.class)) || type.equals(ClassName.BYTE)) {
            return "aByte";
        } else if (type.equals(ClassName.get(Short.class)) || type.equals(ClassName.SHORT)) {
            return "aShort";
        } else if (type.equals(ClassName.get(Integer.class)) || type.equals(ClassName.INT)) {
            return "anInt";
        } else if (type.equals(ClassName.get(Long.class)) || type.equals(ClassName.LONG)) {
            return "aLong";
        } else if (type.equals(ClassName.get(Float.class)) || type.equals(ClassName.FLOAT)) {
            return "aFloat";
        } else if (type.equals(ClassName.get(Double.class)) || type.equals(ClassName.DOUBLE)) {
            return "aDouble";
        } else if (type.equals(ClassName.get(Character.class)) || type.equals(ClassName.CHAR)) {
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
