package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.field.ModelField;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModeledClass {

    private final TypeElement typeElement;

    private final List<FieldCustomization> fieldCustomizations;

    private final ModelFieldProvider fieldProvider = new ModelFieldProvider();

    public ModeledClass(TypeElement typeElement, List<FieldCustomization> fieldCustomizations) {
        this.typeElement = typeElement;
        this.fieldCustomizations = fieldCustomizations;
    }

    public List<ModelField> fields() {
        return declaredFieldsOf(typeElement)
                .map(this::toModelField)
                .collect(HidingFieldsGrouper.GROUPER)
                .values().stream().flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public List<ModeledClass> innerClasses() {
        return typeElement.getEnclosedElements().stream()
                .filter(this::isModeledInnerClass)
                .map(TypeElement.class::cast)
                .map(element -> new ModeledClass(element, new ArrayList<>()))
                .collect(Collectors.toList());
    }

    private boolean isModeledInnerClass(Element element) {
        return element.getKind().equals(ElementKind.CLASS) &&
                element.getModifiers().contains(Modifier.PUBLIC) &&
                element.getModifiers().contains(Modifier.STATIC);
    }

    private Stream<VariableElement> declaredFieldsOf(TypeElement typeElement) {
        final TypeMirror superclass = typeElement.getSuperclass();
        if (TypeKind.NONE.equals(superclass.getKind())) {
            return Stream.empty();
        }
        final TypeElement superTypeElement = (TypeElement) ((DeclaredType) superclass).asElement();
        final Stream<VariableElement> superTypeFields = declaredFieldsOf(superTypeElement);
        final Stream<VariableElement> builtInFields = typeElement.getEnclosedElements().stream()
                .filter(this::isVariableElement)
                .map(VariableElement.class::cast);
        return Stream.concat(superTypeFields, builtInFields);
    }

    private boolean isVariableElement(Element element) {
        return element.getKind().equals(ElementKind.FIELD) && !element.getModifiers().contains(Modifier.STATIC);
    }

    private ModelField toModelField(VariableElement variableElement) {
        final FieldCustomization customization = findCustomizationFor(variableElement);
        final ModelField modelField = fieldProvider.provide(variableElement.asType(), customization);
        modelField.setInherited(!variableElement.getEnclosingElement().equals(typeElement));
        return modelField;
    }

    private FieldCustomization findCustomizationFor(VariableElement variableElement) {
        return fieldCustomizations.stream()
                .filter(c -> c.matches(variableElement.getEnclosingElement().toString(), variableElement.getSimpleName().toString()))
                .findFirst()
                .orElseGet(() -> defaultCustomization(variableElement));
    }

    private FieldCustomization defaultCustomization(VariableElement variableElement) {
        return new FieldCustomization(variableElement.getEnclosingElement().toString(), variableElement.getSimpleName().toString());
    }

    public String packageName() {
        return className().packageName();
    }

    public ClassName className() {
        return ClassName.get(typeElement);
    }
}
