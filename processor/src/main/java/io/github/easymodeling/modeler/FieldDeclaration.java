package io.github.easymodeling.modeler;

import javax.lang.model.element.VariableElement;
import javax.lang.model.type.TypeMirror;

public class FieldDeclaration {

    private String className;

    private String fieldName;

    private TypeMirror typeMirror;

    @SuppressWarnings("unused")
    public FieldDeclaration() {
    }

    public FieldDeclaration(VariableElement element) {
        this.className = element.getEnclosingElement().toString();
        this.fieldName = element.getSimpleName().toString();
        this.typeMirror = element.asType();
    }

    public String getFieldName() {
        return fieldName;
    }

    public String getClassName() {
        return className;
    }

    public TypeMirror getTypeMirror() {
        return typeMirror;
    }
}
