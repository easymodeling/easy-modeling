package xyz.v2my.easymodeling.factory.field.numeric;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.field.PrimitiveField;

public class BooleanField extends PrimitiveField {

    public BooleanField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    protected String staticInitializer() {
        return "aBoolean";
    }
}
