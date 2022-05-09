package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.field.ModelField;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModeledClass {

    private final TypeElement typeElement;

    private final List<FieldCustomization> fieldCustomizations;

    public ModeledClass(TypeElement typeElement, List<FieldCustomization> fieldCustomizations) {
        this.typeElement = typeElement;
        this.fieldCustomizations = fieldCustomizations;
    }

    public List<ModelField> fields() {
        final List<ModelField> modelFields = declaredFieldsOf(typeElement).stream()
                .map(this::toModelField)
                .collect(Collectors.toList());
        // TODO: 08.05.22 wait for refactoring
        modelFields.stream()
                .collect(Collectors.groupingBy(ModelField::fieldName))
                .entrySet().stream()
                .filter(entry -> entry.getValue().size() > 1)
                .forEach(entry -> {
                    final List<ModelField> hiddenFields = entry.getValue();
                    hiddenFields.stream().filter(ModelField::isInherited).forEach(ModelField::setHidden);
                });
        return modelFields;
    }

    private List<VariableElement> declaredFieldsOf(TypeElement typeElement) {
        final TypeMirror superclass = typeElement.getSuperclass();
        if (TypeKind.NONE.equals(superclass.getKind())) {
            return Collections.emptyList();
        }
        final TypeElement superTypeElement = (TypeElement) ((DeclaredType) superclass).asElement();
        final List<VariableElement> superTypeFields = declaredFieldsOf(superTypeElement);
        final List<VariableElement> builtInFields = typeElement.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(VariableElement.class::cast)
                .collect(Collectors.toList());
        return Stream.concat(superTypeFields.stream(), builtInFields.stream())
                .collect(Collectors.toList());
    }

    private ModelField toModelField(VariableElement variableElement) {
        // TODO: 07.05.22 create singleton instance of ModelFieldProvider
        final ModelFieldProvider fieldProvider = new ModelFieldProvider();
        final FieldCustomization customization = fieldCustomizations.stream()
                .filter(c -> c.matches(variableElement))
                .findFirst()
                .orElse(FieldCustomization.of(variableElement));
        final ModelField modelField = fieldProvider.provide(variableElement.asType(), customization);
        modelField.setInherited(!variableElement.getEnclosingElement().equals(typeElement));
        return modelField;
    }

    public String packageName() {
        return className().packageName();
    }

    public ClassName className() {
        return ClassName.get(typeElement);
    }
}
