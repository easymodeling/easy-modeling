package io.github.easymodeling.modeler.provider.mock;

import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.Name;
import javax.lang.model.type.TypeMirror;
import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Set;

public class ElementMock implements Element {

    private final ElementKind kind;

    ElementMock(ElementKind kind) {
        this.kind = kind;
    }

    @Override
    public ElementKind getKind() {
        return this.kind;
    }

    @Override
    public TypeMirror asType() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Modifier> getModifiers() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Name getSimpleName() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Element getEnclosingElement() {
        throw new UnsupportedOperationException();
    }

    @Override
    public List<? extends Element> getEnclosedElements() {
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

    @Override
    public <R, P> R accept(ElementVisitor<R, P> v, P p) {
        throw new UnsupportedOperationException();
    }
}
