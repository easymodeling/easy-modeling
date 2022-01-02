package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import java.util.List;

public abstract class ContainerField<CONTAINER> extends InitializableContainerField {

    public ContainerField() {
    }

    public ContainerField(TypeName type, FieldWrapper field, List<ModelField> valueFields) {
        super(type, field, valueFields);
    }

    public abstract ContainerField<?> create(TypeName type, FieldWrapper field, List<ModelField> nestedFields);
}
