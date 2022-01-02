package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class PlainType<FIELD> extends InitializablePlainType<FIELD> {

    protected PlainType() {
    }

    protected PlainType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public abstract PlainType<FIELD> create(TypeName type, FieldWrapper field);

}
