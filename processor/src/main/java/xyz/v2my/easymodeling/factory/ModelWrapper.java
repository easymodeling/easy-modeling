package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.Model;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ModelWrapper {

    private ClassName modelTypeName;

    private List<FieldPattern> fieldPatterns;

    private List<FieldDeclaration> fieldDeclarations;

    @SuppressWarnings("unused")
    public ModelWrapper() {
    }

    public ModelWrapper(Model model, TypeElement typeElement) {
        this.fieldPatterns = Optional.ofNullable(model).map(this::fieldPatternsOf).orElse(Collections.emptyList());
        this.modelTypeName = ClassName.get(typeElement);
        this.fieldDeclarations = typeElement.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(VariableElement.class::cast)
                .map(FieldDeclaration::new)
                .collect(Collectors.toList());
    }

    private List<FieldPattern> fieldPatternsOf(Model model) {
        return Arrays.stream(model.fields()).map(FieldPattern::of).collect(Collectors.toList());
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
