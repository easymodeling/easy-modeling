package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.processor.AnnoModelWrapper;
import io.github.easymodeling.processor.ProcessingException;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeKind;
import javax.lang.model.type.TypeMirror;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.easymodeling.log.ProcessorLogger.log;

public class ModeledClass {

    private ClassName modelTypeName;

    private List<ModelField> fields;

    @SuppressWarnings("unused")
    public ModeledClass() {
    }

    public ModeledClass(AnnoModelWrapper model, TypeElement typeElement) {
        this.modelTypeName = ClassName.get(typeElement);
        this.fields = initBuilderFields(model, typeElement);
    }

    private List<ModelField> initBuilderFields(AnnoModelWrapper model, TypeElement typeElement) {
        try {
            final ModelFieldProvider fieldProvider = new ModelFieldProvider();
            log.info("Create modeler for " + this.getModelTypeName());
            final Map<String, FieldCustomization> customizedFields =
                    model.getFieldCustomizations().stream().collect(Collectors.toMap(FieldCustomization::qualifiedName, Function.identity()));
            return fieldsOf(typeElement)
                    .stream()
                    .map(element -> {
                        final FieldCustomization emptyFieldCustomization = FieldCustomization.of(element);
                        final FieldCustomization fieldCustomization = customizedFields.getOrDefault(emptyFieldCustomization.qualifiedName(), emptyFieldCustomization);
                        // TODO: 06.05.22 try to remove the second argument
                        return fieldProvider.provide(element.asType(), fieldCustomization);
                    })
                    .collect(Collectors.toList());
        } catch (IllegalStateException e) {
            // Do not support multiple declarations of the same field,
            // let's see if it is possible or necessary to support it in the future
            throw new ProcessingException("Duplicated fields declaration: " + e.getMessage());
        }
    }

    private List<VariableElement> fieldsOf(TypeElement typeElement) {
        final TypeMirror superclass = typeElement.getSuperclass();
        if (TypeKind.NONE.equals(superclass.getKind())) {
            return Collections.emptyList();
        }
        final TypeElement superTypeElement = (TypeElement) ((DeclaredType) superclass).asElement();
        final List<VariableElement> superTypeFields = fieldsOf(superTypeElement);
        final List<VariableElement> builtInFields = typeElement.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(VariableElement.class::cast)
                .collect(Collectors.toList());
        return Stream.concat(superTypeFields.stream(), builtInFields.stream())
                .collect(Collectors.toList());
    }

    public List<ModelField> getFields() {
        return fields;
    }

    public ClassName getModelTypeName() {
        return modelTypeName;
    }

    public String getModelPackage() {
        return modelTypeName.packageName();
    }
}
