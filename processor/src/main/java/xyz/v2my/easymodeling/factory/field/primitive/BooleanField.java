package xyz.v2my.easymodeling.factory.field.primitive;

import com.squareup.javapoet.TypeName;

public class BooleanField extends PrimitiveField {

    public BooleanField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    protected String staticInitializer() {
        return "aBoolean";
    }
}
