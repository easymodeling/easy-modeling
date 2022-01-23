package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.Model;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ModelWrapper {

    private ClassName modelTypeName;

    private List<FieldPattern> fieldPatterns;

    private List<FieldDeclaration> fieldDeclarations;

    @SuppressWarnings("unused")
    public ModelWrapper() {
    }

    public ModelWrapper(Model model, TypeElement typeElement) {
        this.fieldPatterns = Arrays.stream(model.fields()).map(FieldPattern::of).collect(Collectors.toList());
        this.modelTypeName = ClassName.get(typeElement);
        this.fieldDeclarations = typeElement.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(VariableElement.class::cast)
                .map(FieldDeclaration::new)
                .collect(Collectors.toList());
    }

    public List<FieldPattern> getFields() {
        return fieldPatterns;
    }

    public ClassName getModelTypeName() {
        return modelTypeName;
    }

    public List<FieldDeclaration> getEnclosedFields() {
        return fieldDeclarations;
    }
}
