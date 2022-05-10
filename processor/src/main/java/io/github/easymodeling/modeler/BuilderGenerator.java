package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import io.github.easymodeling.ReflectionUtil;
import io.github.easymodeling.modeler.field.BuilderMember;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.StatementProvider;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public class BuilderGenerator {

    protected final ClassName className;

    protected final List<ModelField> fields;

    public BuilderGenerator(ClassName className, List<ModelField> fields) {
        this.className = className;
        this.fields = fields;
    }

    protected TypeSpec createBuilder() {
        final TypeSpec.Builder builder = TypeSpec.classBuilder(GenerationPatterns.BUILDER_CLASS_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .addMethod(builderConstructor())
                .addMethod(buildMethod())
                .addFields(builderFields())
                .addMethods(builderSetters());
        return builder.build();
    }

    private MethodSpec builderConstructor() {
        final ParameterSpec constructorParameter = ParameterSpec.builder(className, "model").build();
        final MethodSpec.Builder builder = MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameter(constructorParameter);
        fields.stream()
                .map(StatementProvider::constructorStatement)
                .forEach(builder::addStatement);
        return builder.build();
    }

    private MethodSpec buildMethod() {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(className);
        builder.addStatement("final $T model = $T.createModelOf($T.class)", className, ReflectionUtil.class, className);
        fields.stream()
                .map(StatementProvider::buildStatement)
                .forEach(builder::addStatement);
        builder.addStatement("return model");
        return builder.build();
    }

    private List<FieldSpec> builderFields() {
        return fields.stream().map(BuilderMember::field).collect(Collectors.toList());
    }

    private List<MethodSpec> builderSetters() {
        return fields.stream()
                .filter(modelField -> !modelField.isHidden())
                .map(BuilderMember::setter)
                .collect(Collectors.toList());
    }
}
