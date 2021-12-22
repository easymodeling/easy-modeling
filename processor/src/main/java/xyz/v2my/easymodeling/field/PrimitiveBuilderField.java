package xyz.v2my.easymodeling.field;

import com.squareup.javapoet.TypeName;

public class PrimitiveBuilderField extends AbstractBuilderField {

    private static boolean DEFAULT_BOOLEAN;

    private static byte DEFAULT_BYTE;

    private static short DEFAULT_SHORT;

    private static int DEFAULT_INT;

    private static long DEFAULT_LONG;

    private static float DEFAULT_FLOAT;

    private static double DEFAULT_DOUBLE;

    private static char DEFAULT_CHAR = 'c';

    public PrimitiveBuilderField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    public String initializer() {
        if (type.toString().equals(boolean.class.toString())) {
            return String.valueOf(DEFAULT_BOOLEAN);
        } else if (type.toString().equals(byte.class.toString())) {
            return "(byte) " + DEFAULT_BYTE;
        } else if (type.toString().equals(short.class.toString())) {
            return "(short) " + DEFAULT_SHORT;
        } else if (type.toString().equals(int.class.toString())) {
            return String.valueOf(DEFAULT_INT);
        } else if (type.toString().equals(long.class.toString())) {
            return String.valueOf(DEFAULT_LONG);
        } else if (type.toString().equals(float.class.toString())) {
            return DEFAULT_FLOAT + "f";
        } else if (type.toString().equals(double.class.toString())) {
            return String.valueOf(DEFAULT_DOUBLE);
        } else if (type.toString().equals(char.class.toString())) {
            return "'" + DEFAULT_CHAR + "'";
        } else {
            throw new IllegalArgumentException(
                    "Class type " + type + " not supported");
        }
    }
}
