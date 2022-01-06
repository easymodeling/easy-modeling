package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import xyz.v2my.easymodeling.factory.field.ModelField;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.Collectors;

public class BuilderClass {

    private static final String BUILDER_NAME_PATTERN = "Builder";

    private final List<ModelField> builderFields;

    private final ClassName returnType;

    public BuilderClass(List<ModelField> builderFields, ClassName builtTypeName) {
        this.builderFields = builderFields;
        this.returnType = builtTypeName;
    }

    public TypeSpec createType() {
        final TypeSpec.Builder builder = TypeSpec.classBuilder(BUILDER_NAME_PATTERN)
                .addModifiers(Modifier.PUBLIC, Modifier.STATIC);
        builder.addMethod(builderAllArgsConstructor())
                .addMethod(buildMethod())
                .addFields(builderFields())
                .addMethods(builderSetters());
        return builder.build();
    }

    private MethodSpec builderAllArgsConstructor() {
        final List<ParameterSpec> constructorParameters = builderFields.stream()
                .map(ModelField::parameter)
                .collect(Collectors.toList());
        final List<CodeBlock> constructorStatements = builderFields.stream()
                .map(ModelField::statement)
                .collect(Collectors.toList());

        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameters(constructorParameters)
                .addStatement(CodeBlock.join(constructorStatements, ";\n")).build();
    }

    private MethodSpec buildMethod() {
        final String constructionParameters = builderFields.stream()
                .map(ModelField::identity)
                .collect(Collectors.joining(", "));
        return MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(returnType)
                .addStatement("return new $N(" + constructionParameters + ")", returnType.simpleName())
                .build();
    }

    private List<FieldSpec> builderFields() {
        return builderFields.stream().map(ModelField::field).collect(Collectors.toList());
    }

    private List<MethodSpec> builderSetters() {
        return builderFields.stream()
                .map(field -> field.setter(BUILDER_NAME_PATTERN))
                .collect(Collectors.toList());
    }
}
