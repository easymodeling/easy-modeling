package io.github.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import io.github.easymodeling.GenerationPatterns;
import io.github.easymodeling.ProcessingException;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.StatementProvider;
import io.github.easymodeling.randomizer.ModelCache;
import io.github.easymodeling.randomizer.Modeler;

import javax.lang.model.element.Modifier;
import javax.lang.model.type.TypeMirror;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static io.github.easymodeling.log.ProcessorLogger.log;

public class ModelerGenerator {

    private final ModelWrapper model;

    private final List<ModelField> fields;

    public ModelerGenerator(ModelWrapper model) {
        this.model = model;
        this.fields = initBuilderFields();
    }

    private List<ModelField> initBuilderFields() {
        final Map<String, FieldPattern> declaredFieldsMap;
        try {
            declaredFieldsMap = model.getFields().stream().collect(Collectors.toMap(FieldPattern::name, Function.identity()));
            // TODO: 29.12.21 merge multiple field declarations
        } catch (IllegalStateException e) {
            throw new ProcessingException("Duplicated fields declaration: " + e.getMessage());
        }
        log.info("Create modeler for " + model.getModelTypeName());
        return model.getEnclosedFields()
                .stream()
                .map(element -> {
                    final String fieldName = element.getFieldName();
                    final TypeMirror typeMirror = element.getTypeMirror();
                    final FieldPattern fieldPattern = declaredFieldsMap.getOrDefault(fieldName, FieldPattern.of(fieldName));
                    return new ModelFieldProvider().provide(typeMirror, fieldPattern);
                })
                .collect(Collectors.toList());
    }

    public TypeSpec createType() {
        final TypeSpec.Builder modeler = TypeSpec.classBuilder(modelerName())
                .addModifiers(Modifier.PUBLIC)
                .superclass(ParameterizedTypeName.get(ClassName.get(Modeler.class), model.getModelTypeName()));
        final TypeSpec builder = new BuilderGenerator(fields, model.getModelTypeName()).createBuilder();
        modeler.addType(builder);

        modeler.addMethod(staticNextMethod());
        modeler.addMethod(staticBuilderMethod());
        modeler.addMethod(populateMethod());
        modeler.addMethod(typeMethod());

        return modeler.build();
    }

    private MethodSpec staticNextMethod() {
        return MethodSpec.methodBuilder(GenerationPatterns.STATIC_NEXT_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(model.getModelTypeName())
                .addStatement("return new $N().$N(null)", modelerName(), GenerationPatterns.BASE_MODELER_NEXT_METHOD_NAME)
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
                .returns(ParameterizedTypeName.get(ClassName.get(Class.class), model.getModelTypeName()))
                .addStatement("return $T.class", model.getModelTypeName())
                .build();
    }

    private MethodSpec populateMethod() {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(GenerationPatterns.MEMBER_POPULATE_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(model.getModelTypeName(), GenerationPatterns.MODEL_PARAMETER_NAME).build())
                .addParameter(ParameterSpec.builder(ModelCache.class, GenerationPatterns.MODEL_CACHE_PARAMETER_NAME).build());
        fields.stream().map(StatementProvider::populateStatement)
                .forEach(builder::addStatement);
        return builder.build();
    }

    private String modelerName() {
        return String.format(GenerationPatterns.MODELER_NAME_PATTERN, model.getModelTypeName().simpleName());
    }
}
