package xyz.v2my.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeSpec;
import xyz.v2my.easymodeling.BaseBuilder;
import xyz.v2my.easymodeling.modeler.field.ModelField;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.Collectors;

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
                .superclass(ParameterizedTypeName.get(ClassName.get(BaseBuilder.class), builtTypeName))
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
        final String constructionParameters = builderFields.stream()
                .map(ModelField::identity)
                .collect(Collectors.joining(", "));
        return MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(builtTypeName)
                .addStatement("return new $N(" + constructionParameters + ")", builtTypeName.simpleName())
                .build();
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
