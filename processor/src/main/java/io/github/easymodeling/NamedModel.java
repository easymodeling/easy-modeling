package io.github.easymodeling;

import javax.lang.model.element.TypeElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.MirroredTypeException;
import javax.lang.model.type.TypeMirror;

public class NamedModel {

    private final String canonicalName;

    private final Model model;

    public NamedModel(Model model) {
        this.canonicalName = classNameOf(model);
        this.model = model;
    }

    public NamedModel(String canonicalName) {
        this.canonicalName = canonicalName;
        this.model = null;
    }

    public String getCanonicalName() {
        return canonicalName;
    }

    public Model getModel() {
        return model;
    }

    @Override
    public int hashCode() {
        return canonicalName.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        if (getClass() != o.getClass()) return false;
        return canonicalName.equals(((NamedModel) o).canonicalName);
    }

    private String classNameOf(Model model) {
        try {
            return model.type().getCanonicalName();
        } catch (MirroredTypeException mte) {
            final TypeMirror typeMirror = mte.getTypeMirror();
            final DeclaredType declaredType = (DeclaredType) typeMirror;
            final TypeElement typeElement = (TypeElement) declaredType.asElement();
            return typeElement.getQualifiedName().toString();
        }
    }
}
