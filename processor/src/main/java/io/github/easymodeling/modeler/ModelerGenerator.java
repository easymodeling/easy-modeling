package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.StatementProvider;
import io.github.easymodeling.randomizer.ModelCache;
import io.github.easymodeling.randomizer.Modeler;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static io.github.easymodeling.log.ProcessorLogger.log;

public class ModelerGenerator extends BuilderGenerator {

    public ModelerGenerator(ClassName className, List<ModelField> fields) {
        super(className, fields);
    }

    public TypeSpec createType() {
        log.info("Create modeler for " + className);
        final TypeSpec.Builder modeler = TypeSpec.classBuilder(modelerName())
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(ClassName.get(Modeler.class), className));
        final TypeSpec builder = createBuilder();
        modeler.addType(builder);

        modeler.addMethod(staticNextMethod());
        modeler.addMethod(staticStreamMethod());
        modeler.addMethod(staticSizedListMethod());
        modeler.addMethod(staticListMethod());
        modeler.addMethod(staticBuilderMethod());
        modeler.addMethod(populateMethod());
        modeler.addMethod(typeMethod());

        return modeler.build();
    }

    private MethodSpec staticNextMethod() {
        return MethodSpec.methodBuilder(GenerationPatterns.STATIC_NEXT_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(className)
                .addStatement("return new $N().$N(null)", modelerName(), GenerationPatterns.BASE_MODELER_NEXT_METHOD_NAME)
                .build();
    }

    private MethodSpec staticStreamMethod() {
        return MethodSpec.methodBuilder(GenerationPatterns.STATIC_STREAM_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(ClassName.get(Stream.class), className))
                .addStatement("return $T.generate(() -> $N())", Stream.class, GenerationPatterns.STATIC_NEXT_METHOD_NAME)
                .build();
    }

    private MethodSpec staticSizedListMethod() {
        final String parameterName = "size";
        final CodeBlock statement = CodeBlock.of("return $N().limit($N).collect($T.toList())",
                GenerationPatterns.STATIC_STREAM_METHOD_NAME, parameterName, Collectors.class);
        return MethodSpec.methodBuilder(GenerationPatterns.STATIC_LIST_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), className))
                .addParameter(ParameterSpec.builder(TypeName.INT, parameterName).build())
                .addStatement(statement)
                .build();
    }

    private MethodSpec staticListMethod() {
        return MethodSpec.methodBuilder(GenerationPatterns.STATIC_LIST_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), className))
                .addStatement("return list(new $T().nextInt(7) + 1)", Random.class)
                .build();
    }

    private MethodSpec staticBuilderMethod() {
        return MethodSpec.methodBuilder(GenerationPatterns.STATIC_BUILDER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get("", GenerationPatterns.BUILDER_CLASS_NAME))
                .addStatement("return new $N.$N($N())", modelerName(), GenerationPatterns.BUILDER_CLASS_NAME, GenerationPatterns.STATIC_NEXT_METHOD_NAME)
                .build();
    }

    private MethodSpec typeMethod() {
        return MethodSpec.methodBuilder(GenerationPatterns.TYPE_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(ParameterizedTypeName.get(ClassName.get(Class.class), className))
                .addStatement("return $T.class", className)
                .build();
    }

    private MethodSpec populateMethod() {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(GenerationPatterns.MEMBER_POPULATE_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(className, GenerationPatterns.MODEL_PARAMETER_NAME).build())
                .addParameter(ParameterSpec.builder(ModelCache.class, GenerationPatterns.MODEL_CACHE_PARAMETER_NAME).build());
        fields.stream().map(StatementProvider::populateStatement)
                .forEach(builder::addStatement);
        return builder.build();
    }

    private String modelerName() {
        return String.format(GenerationPatterns.MODELER_NAME_PATTERN, className.simpleName());
    }
}
