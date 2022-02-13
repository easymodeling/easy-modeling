package xyz.v2my.easymodeling.modeler.provider.mock;

import com.squareup.javapoet.ArrayTypeName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.type.ArrayType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import java.lang.annotation.Annotation;
import java.util.List;

public class ArrayTypeMock implements ArrayType {

    private final TypeMirror componentType;

    ArrayTypeMock(TypeMirror componentType) {
        this.componentType = componentType;
    }

    @Override
    public TypeMirror getComponentType() {
        return componentType;
    }

    @Override
    public TypeKind getKind() {
        return TypeKind.ARRAY;
    }

    @Override
    public <R, P> R accept(TypeVisitor<R, P> v, P p) {
        return (R) ArrayTypeName.of(TypeName.get(componentType));
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
