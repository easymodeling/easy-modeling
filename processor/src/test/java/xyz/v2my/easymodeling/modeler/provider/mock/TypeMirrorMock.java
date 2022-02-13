package xyz.v2my.easymodeling.modeler.provider.mock;

import com.squareup.javapoet.TypeName;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import java.lang.annotation.Annotation;
import java.util.List;

public class TypeMirrorMock implements TypeMirror {

    private TypeKind kind;

    private TypeName typeName;

    public TypeMirrorMock() {
    }

    TypeMirrorMock(TypeKind kind, TypeName typeName) {
        this.kind = kind;
        this.typeName = typeName;
    }

    @Override
    public TypeKind getKind() {
        return this.kind;
    }

    @Override
    public <R, P> R accept(TypeVisitor<R, P> v, P p) {
        return (R) this.typeName;
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        return null;
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        return null;
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
        return null;
    }
}
