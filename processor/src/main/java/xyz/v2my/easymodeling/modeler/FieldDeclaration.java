package xyz.v2my.easymodeling.modeler;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class FieldDeclaration {

    private String fieldName;

    private TypeMirror typeMirror;

    @SuppressWarnings("unused")
    public FieldDeclaration() {
    }

    public FieldDeclaration(VariableElement element) {
        this.fieldName = element.getSimpleName().toString();
        this.typeMirror = element.asType();
    }

    public String getFieldName() {
        return fieldName;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }
}
