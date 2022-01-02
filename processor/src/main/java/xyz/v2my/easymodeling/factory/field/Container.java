package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import java.util.List;

public abstract class Container<T> extends InitializableContainer {

    protected Container() {
    }

    protected Container(TypeName type, FieldWrapper field, List<Type> valueFields) {
        super(type, field, valueFields);
    }

    public abstract Container<T> create(TypeName type, FieldWrapper field, List<Type> nestedFields);
}
