package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.processor.NamedModel;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModelWrapper {

    private ClassName modelTypeName;

    private List<FieldPattern> fieldPatterns;

    private List<FieldDeclaration> fieldDeclarations;

    @SuppressWarnings("unused")
    public ModelWrapper() {
    }

    public ModelWrapper(NamedModel model, TypeElement typeElement) {
        this.fieldPatterns = fieldPatternsOf(model);
        this.modelTypeName = ClassName.get(typeElement);
        this.fieldDeclarations = fieldsOf(typeElement);
    }

    private List<FieldDeclaration> fieldsOf(TypeElement typeElement) {
        final TypeMirror superclass = typeElement.getSuperclass();
        if (TypeKind.NONE.equals(superclass.getKind())) {
            return Collections.emptyList();
        }
        final TypeElement superTypeElement = (TypeElement) ((DeclaredType) superclass).asElement();
        final List<FieldDeclaration> superTypeFields = fieldsOf(superTypeElement);
        final List<FieldDeclaration> builtInFields = typeElement.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(VariableElement.class::cast)
                .map(FieldDeclaration::new)
                .collect(Collectors.toList());
        return Stream.concat(superTypeFields.stream(), builtInFields.stream())
                .collect(Collectors.toList());
    }

    private List<FieldPattern> fieldPatternsOf(NamedModel namedModel) {
        return Optional.ofNullable(namedModel.getModel())
                .map(model -> Arrays.asList(model.fields()))
                .orElse(Collections.emptyList())
                .stream()
                .map(field -> FieldPattern.of(namedModel.getCanonicalName(), field))
                .collect(Collectors.toList());
    }

    public List<FieldPattern> getFields() {
        return fieldPatterns;
    }

    public ClassName getModelTypeName() {
        return modelTypeName;
    }

    public String getModelPackage() {
        return modelTypeName.packageName();
    }

    public List<FieldDeclaration> getEnclosedFields() {
        return fieldDeclarations;
    }
}
