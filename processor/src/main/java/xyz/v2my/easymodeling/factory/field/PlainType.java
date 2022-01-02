package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class PlainType<T> extends InitializablePlainType<T> {

    protected PlainType() {
    }

    protected PlainType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public abstract PlainType<T> create(TypeName type, FieldWrapper field);

}
