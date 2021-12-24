package xyz.v2my.easymodeling.factory.field;

import com.google.common.collect.Sets;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Randomizer;
import xyz.v2my.easymodeling.factory.Import;

import java.util.Set;

public class PrimitiveBuilderField extends AbstractBuilderField {

    private final String method;

    public PrimitiveBuilderField(TypeName type, String name) {
        super(type, name);
        this.method = init();
    }

    public String init() {
        if (type.toString().equals(boolean.class.toString())) {
            return "aBoolean";
        } else if (type.toString().equals(byte.class.toString())) {
            return "aByte";
        } else if (type.toString().equals(short.class.toString())) {
            return "aShort";
        } else if (type.toString().equals(int.class.toString())) {
            return "anInt";
        } else if (type.toString().equals(long.class.toString())) {
            return "aLong";
        } else if (type.toString().equals(float.class.toString())) {
            return "aFloat";
        } else if (type.toString().equals(double.class.toString())) {
            return "aDouble";
        } else if (type.toString().equals(char.class.toString())) {
            return "aChar";
        } else {
            throw new IllegalArgumentException("Class type " + type + " not supported");
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
