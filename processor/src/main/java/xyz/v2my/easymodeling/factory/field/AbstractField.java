package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class AbstractField {

    protected TypeName type;

    protected String name;

    protected FieldWrapper field;

    public AbstractField() {
    }

    protected AbstractField(TypeName type, FieldWrapper field) {
        this.type = type;
        this.name = field.name();
        this.field = field;
    }
}
