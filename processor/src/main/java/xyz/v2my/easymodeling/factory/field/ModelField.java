package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class ModelField extends BuilderField implements InitializableField {

    public ModelField() {
    }

    public ModelField(TypeName type, FieldWrapper field) {
        super(type, field);
    }
}
