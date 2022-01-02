package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import java.util.List;

public abstract class Container<CONTAINER> extends InitializableContainer {

    public Container() {
    }

    public Container(TypeName type, FieldWrapper field, List<Type> valueFields) {
        super(type, field, valueFields);
    }

    public abstract Container<CONTAINER> create(TypeName type, FieldWrapper field, List<Type> nestedFields);
}
