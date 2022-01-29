package xyz.v2my.easymodeling.modeler;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;

public class FieldDeclaration {

    private String fieldName;

    private TypeName typeName;

    @SuppressWarnings("unused")
    public FieldDeclaration() {
    }

    public FieldDeclaration(VariableElement element) {
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
