package io.github.easymodeling.modeler.provider.mock;

import com.google.common.collect.Lists;
import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.type.TypeVisitor;
import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

public class DeclaredTypeMock implements DeclaredType {

    private final TypeKind kind;

    private final TypeName typeName;

    private final ElementKind elementKind;

    private final List<? extends TypeMirror> typeArguments;

    DeclaredTypeMock(Class<?> clazz) {
        this.kind = TypeKind.DECLARED;
        this.typeName = TypeName.get(clazz);
        this.elementKind = ElementKind.CLASS;
        this.typeArguments = Lists.newArrayList();
    }

    public DeclaredTypeMock(Class<?> clazz, TypeMirror[] elementType) {
        this.kind = TypeKind.DECLARED;
        this.typeName = ParameterizedTypeName.get(ClassName.get(clazz), Arrays.stream(elementType).map(TypeName::get).toArray(TypeName[]::new));
        this.elementKind = ElementKind.CLASS;
        this.typeArguments = Lists.newArrayList(elementType);
    }

    @Override
    public TypeKind getKind() {
        return this.kind;
    }

    @Override
    public Element asElement() {
        return new ElementMock(this.elementKind);
    }

    @Override
    public <R, P> R accept(TypeVisitor<R, P> v, P p) {
        return (R) typeName;
    }

    @Override
    public List<? extends TypeMirror> getTypeArguments() {
        return this.typeArguments;
    }

    @Override
    public TypeMirror getEnclosingType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<? extends AnnotationMirror> getAnnotationMirrors() {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A extends Annotation> A getAnnotation(Class<A> annotationType) {
        throw new UnsupportedOperationException();
    }

    @Override
    public <A extends Annotation> A[] getAnnotationsByType(Class<A> annotationType) {
        throw new UnsupportedOperationException();
    }
}
