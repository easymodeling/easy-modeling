package xyz.v2my.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import xyz.v2my.easymodeling.ReflectionUtil;
import xyz.v2my.easymodeling.modeler.field.ModelField;

import javax.lang.model.element.Modifier;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static xyz.v2my.easymodeling.GenerationPatterns.BUILDER_CLASS_NAME;

public class BuilderGenerator {

    private final List<ModelField> builderFields;

    private final ClassName builtTypeName;

    public BuilderGenerator(List<ModelField> builderFields, ClassName builtTypeName) {
        this.builderFields = builderFields;
        this.builtTypeName = builtTypeName;
    }

    public TypeSpec createBuilder() {
        final TypeSpec.Builder builder = TypeSpec.classBuilder(BUILDER_CLASS_NAME)
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
                .map(ModelField::constructorStatement)
                .forEach(builder::addStatement);
        return builder.build();
    }

    private MethodSpec buildMethod() {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(builtTypeName);
        builder.beginControlFlow("try");
        builder.addStatement("final $T model = $T.createModelOf($T.class)", builtTypeName, ReflectionUtil.class, builtTypeName);
        builderFields.stream()
                .map(ModelField::buildStatement)
                .forEach(builder::addStatement);
        builder.addStatement("return model");
        final CodeBlock exceptions = Stream.of(
                        InvocationTargetException.class,
                        InstantiationException.class,
                        IllegalAccessException.class,
                        NoSuchFieldException.class)
                .map(e -> CodeBlock.of("$T", e))
                .collect(CodeBlock.joining(" | "));
        builder.nextControlFlow("catch ($L e)", exceptions);
        builder.addStatement(CodeBlock.of("throw new $T(e)", RuntimeException.class));
        builder.endControlFlow();
        return builder.build();
    }

    private List<FieldSpec> builderFields() {
        return builderFields.stream().map(ModelField::field).collect(Collectors.toList());
    }

    private List<MethodSpec> builderSetters() {
        return builderFields.stream()
                .map(ModelField::setter)
                .collect(Collectors.toList());
    }
}
