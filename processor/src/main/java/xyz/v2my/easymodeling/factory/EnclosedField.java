package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

public class EnclosedField {

    final String fieldName;

    final TypeName typeName;

    public EnclosedField(VariableElement element) {
        this.fieldName = element.getSimpleName().toString();
        this.typeName = TypeName.get(element.asType());
    }

    public String getFieldName() {
        return fieldName;
    }

    public TypeName getTypeName() {
        return typeName;
    }
}
