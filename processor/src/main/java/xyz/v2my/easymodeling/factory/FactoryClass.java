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

import static xyz.v2my.easymodeling.GenerationPatterns.BUILDER_METHOD_NAME;
import static xyz.v2my.easymodeling.GenerationPatterns.FACTORY_CLASS_NAME_PATTERN;

public class FactoryClass {

    private final ModelWrapper model;

    private final List<ModelField> fields;

    private final BuilderClass builderClass;

    public FactoryClass(ModelWrapper model) {
        this.model = model;
        final List<ModelField> fields = initBuilderFields();
        this.fields = fields;
        this.builderClass = new BuilderClass(fields, model.getModelTypeName());
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
        final String factoryTypeName = String.format(FACTORY_CLASS_NAME_PATTERN, model.getModelTypeName().simpleName());
        final TypeSpec.Builder factory = TypeSpec.classBuilder(factoryTypeName).addModifiers(Modifier.PUBLIC);
        final TypeSpec innerBuilder = builderClass.createType();
        factory.addType(innerBuilder);

        factory.addMethod(nextMethod());
        factory.addMethod(builderMethod(innerBuilder.name));

        return factory.build();
    }

    private MethodSpec builderMethod(String builderName) {
        final CodeBlock builderParameters = fields.stream()
                .map(ModelField::initialValue)
                .collect(CodeBlock.joining(", "));
        return MethodSpec.methodBuilder(BUILDER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get("", builderName))
                .addStatement("return new $N($L)", builderName, builderParameters)
                .build();
    }

    private MethodSpec nextMethod() {
        return MethodSpec.methodBuilder("next")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(model.getModelTypeName())
                .addStatement("return $N().build()", BUILDER_METHOD_NAME)
                .build();
    }
}
