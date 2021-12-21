package xyz.v2my.easymodeling;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import lombok.AllArgsConstructor;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
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
        final List<BuilderField> nonStaticFields = clazz.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(BuilderField::new)
                .collect(Collectors.toList());

        final List<ParameterSpec> constructorParameters = nonStaticFields.stream()
                .map(BuilderField::constructorParameter)
                .collect(Collectors.toList());
        final List<CodeBlock> constructorStatements = nonStaticFields.stream()
                .map(BuilderField::constructorStatement)
                .collect(Collectors.toList());

        final MethodSpec.Builder constructorBuilder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameters(constructorParameters)
                .addStatement(CodeBlock.join(constructorStatements, ";\n"));

        builder.addMethod(constructorBuilder.build());

        final String constructionParameters = nonStaticFields.stream()
                .map(BuilderField::constructorVariable)
                .collect(Collectors.joining(", "));
        final MethodSpec buildMethod = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(clazz))
                .addStatement("return new $N(" + constructionParameters + ")", clazz.getSimpleName())
                .build();
        builder.addMethod(buildMethod);

        final List<FieldSpec> builderFields = nonStaticFields.stream().map(BuilderField::builderField).collect(Collectors.toList());
        builder.addFields(builderFields);
        final List<MethodSpec> builderSetters = nonStaticFields.stream().map(field -> field.builderSetter(builderTypeName)).collect(Collectors.toList());
        builder.addMethods(builderSetters);

        return builder.build();
    }
}
