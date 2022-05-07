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
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ModeledClass {

    private TypeElement typeElement;

    private List<FieldCustomization> fieldCustomizations;

    @SuppressWarnings("unused")
    public ModeledClass() {
    }

    public ModeledClass(TypeElement typeElement, List<FieldCustomization> fieldCustomizations) {
        this.typeElement = typeElement;
        this.fieldCustomizations = fieldCustomizations;
    }

    public List<ModelField> fields() {
        final ModelFieldProvider fieldProvider = new ModelFieldProvider();
        final Map<String, FieldCustomization> customizedFields =
                fieldCustomizations.stream().distinct().collect(Collectors.toMap(FieldCustomization::qualifiedName, Function.identity()));
        return fieldsOf(typeElement)
                .stream()
                .map(element -> {
                    final FieldCustomization emptyFieldCustomization = FieldCustomization.of(element);
                    final FieldCustomization fieldCustomization = customizedFields.getOrDefault(emptyFieldCustomization.qualifiedName(), emptyFieldCustomization);
                    // TODO: 06.05.22 try to remove the second argument
                    return fieldProvider.provide(element.asType(), fieldCustomization);
                })
                .collect(Collectors.toList());
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

    public String packageName() {
        return className().packageName();
    }

    public ClassName className() {
        return ClassName.get(typeElement);
    }
}
