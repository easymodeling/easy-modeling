package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;

public class CharField extends PrimitiveField {

    public CharField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    protected String staticInitializer() {
        return "aChar";
    }
}
