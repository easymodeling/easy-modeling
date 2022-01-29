package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import xyz.v2my.easymodeling.ProcessingException;
import xyz.v2my.easymodeling.factory.field.ModelField;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static xyz.v2my.easymodeling.GenerationPatterns.BUILDER_CLASS_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.MEMBER_BUILDER_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.MEMBER_NEXT_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.MODELER_NAME_PATTERN;
import static xyz.v2my.easymodeling.GenerationPatterns.STATIC_BUILDER_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.STATIC_NEXT_METHOD_NAME;

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
        final TypeSpec.Builder modeler = TypeSpec.classBuilder(modelerName()).addModifiers(Modifier.PUBLIC);
        final BuilderGenerator builderGenerator = new BuilderGenerator(fields, model.getModelTypeName());
        final TypeSpec builder = builderGenerator.createType();
        modeler.addType(builder);

        modeler.addMethod(staticNextMethod());
        modeler.addMethod(staticBuilderMethod());
        modeler.addMethod(memberNextMethod());
        modeler.addMethod(memberBuilderMethod());

        return modeler.build();
    }

    private MethodSpec staticNextMethod() {
        return MethodSpec.methodBuilder(STATIC_NEXT_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(model.getModelTypeName())
                .addStatement("return new $N().$N()", modelerName(), MEMBER_NEXT_METHOD_NAME)
                .build();
    }

    private MethodSpec staticBuilderMethod() {
        return MethodSpec.methodBuilder(STATIC_BUILDER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get("", BUILDER_CLASS_NAME))
                .addStatement("return new $N().$N()", modelerName(), MEMBER_BUILDER_METHOD_NAME)
                .build();
    }

    private MethodSpec memberNextMethod() {
        return MethodSpec.methodBuilder(MEMBER_NEXT_METHOD_NAME)
                .addModifiers(Modifier.PROTECTED)
                .returns(model.getModelTypeName())
                .addStatement("return $N().build()", MEMBER_BUILDER_METHOD_NAME)
                .build();
    }

    private MethodSpec memberBuilderMethod() {
        final CodeBlock builderParameters = fields.stream()
                .map(ModelField::initialValue)
                .collect(CodeBlock.joining(", "));
        return MethodSpec.methodBuilder(MEMBER_BUILDER_METHOD_NAME)
                .addModifiers(Modifier.PROTECTED)
                .returns(ClassName.get("", BUILDER_CLASS_NAME))
                .addStatement("return new $N($L)", BUILDER_CLASS_NAME, builderParameters)
                .build();
    }

    private String modelerName() {
        return String.format(MODELER_NAME_PATTERN, model.getModelTypeName().simpleName());
    }
}
