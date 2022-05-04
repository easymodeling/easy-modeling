package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import io.github.easymodeling.processor.GenerationPatterns;
import io.github.easymodeling.ReflectionUtil;
import io.github.easymodeling.modeler.field.BuilderMember;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.StatementProvider;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public class BuilderGenerator {

    private final List<ModelField> builderFields;

    private final ClassName builtTypeName;

    public BuilderGenerator(List<ModelField> builderFields, ClassName builtTypeName) {
        this.builderFields = builderFields;
        this.builtTypeName = builtTypeName;
    }

    public TypeSpec createBuilder() {
        final TypeSpec.Builder builder = TypeSpec.classBuilder(GenerationPatterns.BUILDER_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addMethod(builderConstructor())
                .addMethod(buildMethod())
                .addFields(builderFields())
                .addMethods(builderSetters());
        return builder.build();
    }

    private MethodSpec builderConstructor() {
        final ParameterSpec constructorParameter = ParameterSpec.builder(builtTypeName, "model").build();
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameter(constructorParameter);
        builderFields.stream()
                .map(StatementProvider::constructorStatement)
                .forEach(builder::addStatement);
        return builder.build();
    }

    private MethodSpec buildMethod() {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(builtTypeName);
        builder.addStatement("final $T model = $T.createModelOf($T.class)", builtTypeName, ReflectionUtil.class, builtTypeName);
        builderFields.stream()
                .map(StatementProvider::buildStatement)
                .forEach(builder::addStatement);
        builder.addStatement("return model");
        return builder.build();
    }

    private List<FieldSpec> builderFields() {
        return builderFields.stream().map(BuilderMember::field).collect(Collectors.toList());
    }

    private List<MethodSpec> builderSetters() {
        return builderFields.stream()
                .map(BuilderMember::setter)
                .collect(Collectors.toList());
    }
}
