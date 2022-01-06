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

    private List<FieldWrapper> annotatedFields;

    private ClassName modelTypeName;

    private List<EnclosedField> enclosedFields;

    public ModelWrapper() {
    }

    public ModelWrapper(Model model, TypeElement typeElement) {
        this.annotatedFields = Arrays.stream(model.fields()).map(FieldWrapper::of).collect(Collectors.toList());
        this.modelTypeName = ClassName.get(typeElement);
        this.enclosedFields = typeElement.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(VariableElement.class::cast)
                .map(EnclosedField::new)
                .collect(Collectors.toList());
    }

    public List<FieldWrapper> getFields() {
        return annotatedFields;
    }

    public ClassName getModelTypeName() {
        return modelTypeName;
    }

    public List<EnclosedField> getEnclosedFields() {
        return enclosedFields;
    }
}
