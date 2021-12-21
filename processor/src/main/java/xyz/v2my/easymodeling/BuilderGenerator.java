package xyz.v2my.easymodeling;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.AllArgsConstructor;

import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.VariableElement;
import java.util.List;
import java.util.stream.Collectors;

public class BuilderGenerator {

    public TypeSpec generate(TypeElement clazz) {
        final String builderTypeName = String.format("EM%sBuilder", clazz.getSimpleName());
        final TypeSpec.Builder builder = TypeSpec.classBuilder(builderTypeName)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);

        if (clazz.getAnnotation(AllArgsConstructor.class) == null) {
            throw new RuntimeException();
        }
        final List<Element> nonStaticFields = clazz.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .collect(Collectors.toList());

        final List<ParameterSpec> constructorParameters = nonStaticFields.stream()
                .map(field -> ParameterSpec.builder(ClassName.get(field.asType()), field.getSimpleName().toString()).build())
                .collect(Collectors.toList());
        final List<CodeBlock> constructorStatements = nonStaticFields.stream()
                .map(field -> CodeBlock.of("this.$N = $N", field.getSimpleName(), field.getSimpleName()))
                .collect(Collectors.toList());

        final MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameters(constructorParameters)
                .addStatement(CodeBlock.join(constructorStatements, ";\n"));

        builder.addMethod(constructorBuilder.build());

        final String constructionParameters = nonStaticFields.stream()
                .map(Element::getSimpleName)
                .collect(Collectors.joining(", "));
        final MethodSpec buildMethod = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(clazz))
                .addStatement("return new $N(" + constructionParameters + ")", clazz.getSimpleName())
                .build();
        builder.addMethod(buildMethod);

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
            builder
                    .addField(builderField)
                    .addMethod(builderSetter);
        }
        return builder.build();
    }
}
