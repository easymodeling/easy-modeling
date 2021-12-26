package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import xyz.v2my.easymodeling.Field;
import xyz.v2my.easymodeling.Model;
import xyz.v2my.easymodeling.ProcessingException;
import xyz.v2my.easymodeling.factory.field.ModelField;

import javax.lang.model.element.ElementKind;
import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

public class FactoryClass implements ImportGenerator {

    private static final String FACTORY_NAME_PATTERN = "EM%s";

    private static final String BUILDER_METHOD_NAME = "builder";

    private final Model model;

    private final TypeElement type;

    private final List<ModelField> modelFields;

    private final BuilderClass builderClass;

    public FactoryClass(Model model, TypeElement type) {
        this.model = model;
        this.type = type;
        final List<ModelField> fields = initBuilderFields(type);
        this.modelFields = fields;
        this.builderClass = new BuilderClass(type, fields);
    }

    private List<ModelField> initBuilderFields(TypeElement type) {
        final Field[] declaredFields = model.fields();
        final Map<String, Field> declaredFieldsMap;
        try {
            declaredFieldsMap = Arrays.stream(declaredFields).collect(Collectors.toMap(Field::name, Function.identity()));
        } catch (IllegalStateException e) {
            throw new ProcessingException("Duplicated fields declaration: " + e.getMessage());
        }
        final BuilderFieldProvider builderFieldProvider = new BuilderFieldProvider();
        return type.getEnclosedElements().stream()
                .filter(element -> element.getKind().equals(ElementKind.FIELD))
                .filter(element -> !element.getModifiers().contains(Modifier.STATIC))
                .map(element -> {
                    final Field fieldDeclaration = declaredFieldsMap.get(element.getSimpleName().toString());
                    return builderFieldProvider.provide(fieldDeclaration, element);
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

    @Override
    public Set<Import> imports() {
        return modelFields.stream().map(ImportGenerator::imports)
                .flatMap(Collection::stream)
                .collect(Collectors.toSet());
    }

    private MethodSpec builderMethod(String builderName) {
        String builderParameters = modelFields.stream()
                .map(ModelField::initializer)
                .collect(Collectors.joining(", "));
        return MethodSpec.methodBuilder(BUILDER_METHOD_NAME)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
                .returns(ClassName.get("", builderName))
                .addStatement("return new $N(" + builderParameters + ")", builderName)
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
