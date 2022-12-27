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
import static io.github.easymodeling.modeler.GenerationPatterns.BASE_MODELER_NEXT_METHOD_NAME;
import static io.github.easymodeling.modeler.GenerationPatterns.BUILDER_CLASS_NAME;
import static io.github.easymodeling.modeler.GenerationPatterns.MEMBER_POPULATE_METHOD_NAME;
import static io.github.easymodeling.modeler.GenerationPatterns.MODELER_JAVADOC;
import static io.github.easymodeling.modeler.GenerationPatterns.MODELER_NAME_PATTERN;
import static io.github.easymodeling.modeler.GenerationPatterns.MODEL_CACHE_PARAMETER_NAME;
import static io.github.easymodeling.modeler.GenerationPatterns.MODEL_PARAMETER_NAME;
import static io.github.easymodeling.modeler.GenerationPatterns.STATIC_BUILDER_METHOD_JAVADOC;
import static io.github.easymodeling.modeler.GenerationPatterns.STATIC_BUILDER_METHOD_NAME;
import static io.github.easymodeling.modeler.GenerationPatterns.STATIC_LIST_METHOD_JAVADOC;
import static io.github.easymodeling.modeler.GenerationPatterns.STATIC_LIST_METHOD_NAME;
import static io.github.easymodeling.modeler.GenerationPatterns.STATIC_NEXT_METHOD_JAVADOC;
import static io.github.easymodeling.modeler.GenerationPatterns.STATIC_NEXT_METHOD_NAME;
import static io.github.easymodeling.modeler.GenerationPatterns.STATIC_SIZED_LIST_METHOD_JAVADOC;
import static io.github.easymodeling.modeler.GenerationPatterns.STATIC_SIZED_STREAM_METHOD_JAVADOC;
import static io.github.easymodeling.modeler.GenerationPatterns.STATIC_STREAM_METHOD_JAVADOC;
import static io.github.easymodeling.modeler.GenerationPatterns.STATIC_STREAM_METHOD_NAME;
import static io.github.easymodeling.modeler.GenerationPatterns.TYPE_METHOD_NAME;

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
        modeler
                .addMethod(staticNextMethod())
                .addMethod(staticSizedStreamMethod())
                .addMethod(staticStreamMethod())
                .addMethod(staticSizedListMethod())
                .addMethod(staticListMethod())
                .addMethod(staticBuilderMethod())
                .addMethod(populateMethod())
                .addMethod(typeMethod());

        return modeler
                .addJavadoc(MODELER_JAVADOC.get())
                .build();
    }

    private MethodSpec staticNextMethod() {
        return MethodSpec.methodBuilder(STATIC_NEXT_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(className)
                .addStatement("return new $N().$N(null)", modelerName(), BASE_MODELER_NEXT_METHOD_NAME)
                .addJavadoc(STATIC_NEXT_METHOD_JAVADOC.apply(className))
                .build();
    }

    private MethodSpec staticSizedStreamMethod() {
        final String parameterName = "size";
        final CodeBlock statement = CodeBlock.of("return $T.generate(() -> $N()).limit($N)",
                Stream.class, STATIC_NEXT_METHOD_NAME, parameterName);
        return MethodSpec.methodBuilder(STATIC_STREAM_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(ClassName.get(Stream.class), className))
                .addParameter(ParameterSpec.builder(TypeName.INT, parameterName).build())
                .addStatement(statement)
                .addJavadoc(STATIC_SIZED_STREAM_METHOD_JAVADOC.apply(className))
                .build();
    }

    private MethodSpec staticStreamMethod() {
        return MethodSpec.methodBuilder(STATIC_STREAM_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(ClassName.get(Stream.class), className))
                .addStatement("return stream(new $T().nextInt(7) + 1)", Random.class)
                .addJavadoc(STATIC_STREAM_METHOD_JAVADOC.apply(className))
                .build();
    }

    private MethodSpec staticSizedListMethod() {
        final String parameterName = "size";
        final CodeBlock statement = CodeBlock.of("return $N($N).collect($T.toList())",
                STATIC_STREAM_METHOD_NAME, parameterName, Collectors.class);
        return MethodSpec.methodBuilder(STATIC_LIST_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), className))
                .addParameter(ParameterSpec.builder(TypeName.INT, parameterName).build())
                .addStatement(statement)
                .addJavadoc(STATIC_SIZED_LIST_METHOD_JAVADOC.apply(className))
                .build();
    }

    private MethodSpec staticListMethod() {
        return MethodSpec.methodBuilder(STATIC_LIST_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ParameterizedTypeName.get(ClassName.get(List.class), className))
                .addStatement("return $N(new $T().nextInt(7) + 1)", STATIC_LIST_METHOD_NAME, Random.class)
                .addJavadoc(STATIC_LIST_METHOD_JAVADOC.apply(className))
                .build();
    }

    private MethodSpec staticBuilderMethod() {
        return MethodSpec.methodBuilder(STATIC_BUILDER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get("", BUILDER_CLASS_NAME))
                .addStatement("return new $N.$N($N())", modelerName(), BUILDER_CLASS_NAME, STATIC_NEXT_METHOD_NAME)
                .addJavadoc(STATIC_BUILDER_METHOD_JAVADOC.apply(className))
                .build();
    }

    private MethodSpec typeMethod() {
        return MethodSpec.methodBuilder(TYPE_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(ParameterizedTypeName.get(ClassName.get(Class.class), className))
                .addStatement("return $T.class", className)
                .build();
    }

    private MethodSpec populateMethod() {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(MEMBER_POPULATE_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(className, MODEL_PARAMETER_NAME).build())
                .addParameter(ParameterSpec.builder(ModelCache.class, MODEL_CACHE_PARAMETER_NAME).build());
        fields.stream().map(StatementProvider::populateStatement)
                .forEach(builder::addStatement);
        return builder.build();
    }

    private String modelerName() {
        return String.format(MODELER_NAME_PATTERN, className.simpleName());
    }
}
