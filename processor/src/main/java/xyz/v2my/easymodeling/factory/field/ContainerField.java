package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class ContainerField<CONTAINER> extends InitializableContainerField {

    public ContainerField() {
    }

    public ContainerField(TypeName type, FieldWrapper field, InitializableField valueField) {
        super(type, field, valueField);
    }

    public abstract ContainerField<?> create(TypeName type, FieldWrapper field, InitializableField valueField);
}
