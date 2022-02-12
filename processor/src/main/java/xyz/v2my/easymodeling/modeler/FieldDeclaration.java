package xyz.v2my.easymodeling.modeler;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class FieldDeclaration {

    private String fieldName;

    private TypeName typeName;

    private TypeMirror typeMirror;

    @SuppressWarnings("unused")
    public FieldDeclaration() {
    }

    public FieldDeclaration(VariableElement element) {
        this.fieldName = element.getSimpleName().toString();
        this.typeName = TypeName.get(element.asType());
        this.typeMirror = element.asType();
    }

    public String getFieldName() {
        return fieldName;
    }

    public TypeName getTypeName() {
        return typeName;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }
}
