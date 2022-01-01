package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class PlainField<FIELD> extends InitializablePlainField<FIELD> {

    public PlainField() {
    }

    protected PlainField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public abstract PlainField<FIELD> create(TypeName type, FieldWrapper field);

}
