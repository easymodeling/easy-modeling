package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.Field;

public class ShortField extends NumericField {

    public ShortField(TypeName type, String name, Field field) {
        super(type, name, field);
    }

    @Override
    protected long ceiling() {
        return Short.MAX_VALUE;
    }

    @Override
    protected long floor() {
        return Short.MIN_VALUE;
    }

    @Override
    protected CodeBlock constantInit(Double c) {
        return CodeBlock.of("(short) $L", c.shortValue());
    }

    @Override
    protected String staticInitializer() {
        return "aShort";
    }
}
