package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import xyz.v2my.easymodeling.factory.field.ModelField;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FactoryType implements ImportGenerator {

    private static final String FACTORY_NAME_PATTERN = "EM%s";

    private static final String BUILDER_METHOD_NAME = "builder";

    private final TypeElement clazz;

    private final List<ModelField> modelFields;

    private final BuilderType builderType;

    public FactoryType(TypeElement clazz, List<ModelField> modelFields) {
        this.clazz = clazz;
        this.modelFields = modelFields;
        this.builderType = new BuilderType(clazz, modelFields);
    }

    public TypeSpec createType() {
        final String factoryTypeName = String.format(FACTORY_NAME_PATTERN, clazz.getSimpleName());
        final TypeSpec.Builder factory = TypeSpec.classBuilder(factoryTypeName).addModifiers(Modifier.PUBLIC);
        final TypeSpec innerBuilder = builderType.createType();
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
                .returns(ClassName.get(clazz))
                .addStatement("return $N().build()", BUILDER_METHOD_NAME)
                .build();
    }
}
