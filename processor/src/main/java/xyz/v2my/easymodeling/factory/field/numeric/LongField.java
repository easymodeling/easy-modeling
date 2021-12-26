package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;

public class LongField extends NumericField {

    public LongField(TypeName type, String name, Field field) {
        super(type, name, field);
    }

    @Override
    protected long ceiling() {
        return Long.MAX_VALUE;
    }

    @Override
    protected long floor() {
        return Long.MIN_VALUE;
    }

    @Override
    protected String staticInitializer() {
        return "aLong";
    }
}
