package xyz.v2my.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import xyz.v2my.easymodeling.ProcessingException;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.StatementProvider;
import xyz.v2my.easymodeling.randomizer.ModelCache;
import xyz.v2my.easymodeling.randomizer.Modeler;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static xyz.v2my.easymodeling.GenerationPatterns.BASE_MODELER_NEXT_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.BUILDER_CLASS_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.MEMBER_POPULATE_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.MODELER_NAME_PATTERN;
import static xyz.v2my.easymodeling.GenerationPatterns.MODEL_CACHE_PARAMETER_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.MODEL_PARAMETER_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.STATIC_BUILDER_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.STATIC_NEXT_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.TYPE_METHOD_NAME;

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
        final ModelFieldProvider modelFieldProvider = new ModelFieldProvider();
        return model.getEnclosedFields()
                .stream()
                .map(element -> {
                    final String fieldName = element.getFieldName();
                    final FieldPattern fieldPattern = declaredFieldsMap.getOrDefault(fieldName, FieldPattern.of(fieldName));
                    return modelFieldProvider.provide(element.getTypeName(), fieldPattern);
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
        return MethodSpec.methodBuilder(STATIC_NEXT_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(model.getModelTypeName())
                .addStatement("return new $N().$N(null)", modelerName(), BASE_MODELER_NEXT_METHOD_NAME)
                .build();
    }

    private MethodSpec staticBuilderMethod() {
        return MethodSpec.methodBuilder(STATIC_BUILDER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get("", BUILDER_CLASS_NAME))
                .addStatement("return new $N.$N($N())", modelerName(), BUILDER_CLASS_NAME, STATIC_NEXT_METHOD_NAME)
                .build();
    }

    private MethodSpec typeMethod() {
        return MethodSpec.methodBuilder(TYPE_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(ParameterizedTypeName.get(ClassName.get(Class.class), model.getModelTypeName()))
                .addStatement("return $T.class", model.getModelTypeName())
                .build();
    }

    private MethodSpec populateMethod() {
        final MethodSpec.Builder builder = MethodSpec.methodBuilder(MEMBER_POPULATE_METHOD_NAME)
                .addAnnotation(Override.class)
                .addModifiers(Modifier.PROTECTED)
                .returns(TypeName.VOID)
                .addParameter(ParameterSpec.builder(model.getModelTypeName(), MODEL_PARAMETER_NAME).build())
                .addParameter(ParameterSpec.builder(ModelCache.class, MODEL_CACHE_PARAMETER_NAME).build());
        fields.stream().map(StatementProvider::populateStatement)
                .forEach(builder::addStatement);
        return builder.build();
    }

    private String modelerName() {
        return String.format(MODELER_NAME_PATTERN, model.getModelTypeName().simpleName());
    }
}
