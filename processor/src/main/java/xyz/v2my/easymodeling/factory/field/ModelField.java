package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class ModelField<FIELD> extends InitializableField<FIELD> {

    public ModelField() {
    }

    protected ModelField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public abstract ModelField<FIELD> create(TypeName type, FieldWrapper field);

}
