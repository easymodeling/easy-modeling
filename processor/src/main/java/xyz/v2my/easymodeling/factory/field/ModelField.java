package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class ModelField extends InitializableField {

    public ModelField() {
    }

    protected ModelField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public abstract ModelField create(TypeName type, FieldWrapper field);

}
