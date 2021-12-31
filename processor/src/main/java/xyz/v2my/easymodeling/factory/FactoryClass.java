package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import com.squareup.javapoet.TypeSpec;
import xyz.v2my.easymodeling.Model;
import xyz.v2my.easymodeling.ProcessingException;
import xyz.v2my.easymodeling.factory.field.ModelField;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FactoryClass {

    private static final String FACTORY_NAME_PATTERN = "EM%s";

    private static final String BUILDER_METHOD_NAME = "builder";

    private final Model model;

    private final TypeElement type;

    private final List<ModelField<?>> modelFields;

    private final BuilderClass builderClass;

    public FactoryClass(Model model, TypeElement type) {
        this.model = model;
        this.type = type;
        final List<ModelField<?>> fields = initBuilderFields(type);
        this.modelFields = fields;
        this.builderClass = new BuilderClass(type, fields);
    }

    private List<ModelField<?>> initBuilderFields(TypeElement type) {
        final List<FieldWrapper> declaredFields = Arrays.stream(model.fields()).map(FieldWrapper::of).collect(Collectors.toList());
        final Map<String, FieldWrapper> declaredFieldsMap;
        try {
            declaredFieldsMap = declaredFields.stream().collect(Collectors.toMap(FieldWrapper::name, Function.identity()));
            // TODO: 29.12.21 merge multiple field declarations
        } catch (IllegalStateException e) {
            throw new ProcessingException("Duplicated fields declaration: " + e.getMessage());
        }
        final BuilderFieldProvider builderFieldProvider = new BuilderFieldProvider();
        return type.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(element -> {
                    final String fieldName = element.getSimpleName().toString();
                    final TypeName typeName = ClassName.get(element.asType());
                    final FieldWrapper field = declaredFieldsMap.getOrDefault(fieldName, FieldWrapper.of(fieldName));
                    return builderFieldProvider.provide(typeName, field);
                })
                .collect(Collectors.toList());
    }

    public TypeSpec createType() {
        final String factoryTypeName = String.format(FACTORY_NAME_PATTERN, type.getSimpleName());
        final TypeSpec.Builder factory = TypeSpec.classBuilder(factoryTypeName).addModifiers(Modifier.PUBLIC);
        final TypeSpec innerBuilder = builderClass.createType();
        factory.addType(innerBuilder);

        factory.addMethod(nextMethod());
        factory.addMethod(builderMethod(innerBuilder.name));

        return factory.build();
    }

    private MethodSpec builderMethod(String builderName) {
        final CodeBlock builderParameters = modelFields.stream()
                .map(ModelField::initialValue)
                .collect(CodeBlock.joining(", "));
        return MethodSpec.methodBuilder(BUILDER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get("", builderName))
                .addStatement("return new $2N($1L)", builderParameters, builderName)
                .build();
    }

    private MethodSpec nextMethod() {
        return MethodSpec.methodBuilder("next")
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get(type))
                .addStatement("return $N().build()", BUILDER_METHOD_NAME)
                .build();
    }
}
