package xyz.v2my.easymodeling.field;

import com.squareup.javapoet.TypeName;

public class PrimitiveBuilderField extends AbstractBuilderField {

    public PrimitiveBuilderField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    public String initializer() {
        if (type.toString().equals(boolean.class.toString())) {
            return "aBoolean()";
        } else if (type.toString().equals(byte.class.toString())) {
            return "aByte()";
        } else if (type.toString().equals(short.class.toString())) {
            return "aShort()";
        } else if (type.toString().equals(int.class.toString())) {
            return "anInt()";
        } else if (type.toString().equals(long.class.toString())) {
            return "aLong()";
        } else if (type.toString().equals(float.class.toString())) {
            return "aFloat()";
        } else if (type.toString().equals(double.class.toString())) {
            return "aDouble()";
        } else if (type.toString().equals(char.class.toString())) {
            return "aChar()";
        } else {
            throw new IllegalArgumentException(
                    "Class type " + type + " not supported");
        }
    }
}
