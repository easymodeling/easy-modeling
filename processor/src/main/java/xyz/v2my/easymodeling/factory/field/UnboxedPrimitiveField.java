package xyz.v2my.easymodeling.factory.field;

import com.google.common.collect.Sets;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Randomizer;
import xyz.v2my.easymodeling.factory.Import;

import java.util.Set;

public class UnboxedPrimitiveField extends AbstractField {

    private final String method;

    public UnboxedPrimitiveField(TypeName type, String name) {
        super(type, name);
        this.method = init();
    }

    public String init() {
        if (type.equals(ClassName.BOOLEAN)) {
            return "aBoolean";
        } else if (type.equals(ClassName.BYTE)) {
            return "aByte";
        } else if (type.equals(ClassName.SHORT)) {
            return "aShort";
        } else if (type.equals(ClassName.INT)) {
            return "anInt";
        } else if (type.equals(ClassName.LONG)) {
            return "aLong";
        } else if (type.equals(ClassName.FLOAT)) {
            return "aFloat";
        } else if (type.equals(ClassName.DOUBLE)) {
            return "aDouble";
        } else if (type.equals(ClassName.CHAR)) {
            return "aChar";
        } else {
            throw new IllegalArgumentException("Primitive " + type + " not supported");
        }
    }

    @Override
    public String initializer() {
        return method + "()";
    }

    @Override
    public Set<Import> imports() {
        return Sets.newHashSet(new Import(Randomizer.class, method));
    }
}
