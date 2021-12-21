package xyz.v2my.easymodeling;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.AllArgsConstructor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import javax.lang.model.util.Elements;
import java.util.stream.Collectors;

public class BuilderGenerator {

    protected final Elements elementUtils;

    public BuilderGenerator(Elements elementUtils) {
        this.elementUtils = elementUtils;
    }

    public void generate(TypeElement clazz, TypeSpec.Builder factoryBuilder) {
        final String builderTypeName = String.format("EM%sBuilder", clazz.getSimpleName());
        final TypeSpec.Builder builderBuilder = TypeSpec.classBuilder(builderTypeName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);

        if (clazz.getAnnotation(AllArgsConstructor.class) == null) {
            throw new RuntimeException();
        }
        final MethodSpec builderConstructor = MethodSpec.constructorBuilder().addModifiers(Modifier.PUBLIC).build();
        builderBuilder.addMethod(builderConstructor);

        final String constructionParameters = clazz.getEnclosedElements().stream()
                .filter(element -> !(!element.getKind().equals(ElementKind.FIELD) || element.getModifiers().contains(Modifier.STATIC)))
                .map(Element::getSimpleName)
                .collect(Collectors.joining(", "));
        final MethodSpec buildMethod = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(clazz))
                .addStatement("return new $N(" + constructionParameters + ")", clazz.getSimpleName())
                .build();
        builderBuilder.addMethod(buildMethod);

        for (Element enclosedElement : clazz.getEnclosedElements()) {
            if (!enclosedElement.getKind().equals(ElementKind.FIELD) || enclosedElement.getModifiers().contains(Modifier.STATIC)) {
                continue;
            }
            final VariableElement field = (VariableElement) enclosedElement;
            final String fieldName = field.getSimpleName().toString();
            final FieldSpec builderField = FieldSpec.builder(ClassName.get(field.asType()), fieldName, Modifier.PRIVATE).build();
            final MethodSpec builderSetter = MethodSpec.methodBuilder(fieldName)
                    .addModifiers(Modifier.PUBLIC)
                    .returns(ClassName.get("", builderTypeName))
                    .addParameter(ClassName.get(field.asType()), fieldName)
                    .addStatement("this.$N = $N", fieldName, fieldName)
                    .addStatement("return this")
                    .build();
            builderBuilder
                    .addField(builderField)
                    .addMethod(builderSetter);
        }
        factoryBuilder.addType(builderBuilder.build());
    }
}
