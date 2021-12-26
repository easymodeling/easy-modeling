package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;

public class IntegerField extends NumericField {

    public IntegerField(TypeName type, String name, Field field) {
        super(type, name, field);
    }

    @Override
    protected long ceiling() {
        return Integer.MAX_VALUE;
    }

    @Override
    protected long floor() {
        return Integer.MIN_VALUE;
    }

    @Override
    protected String staticInitializer() {
        return "anInt";
    }
}
