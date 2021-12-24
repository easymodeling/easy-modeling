package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;

public class GenericField extends AbstractField {

    public GenericField(TypeName type, String name) {
        super(type, name);
    }

    @Override
    public String initializer() {
        return "null";
    }
}
