package xyz.v2my.easymodeling.factory;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import xyz.v2my.easymodeling.factory.field.Type;

import javax.lang.model.element.Modifier;
import javax.lang.model.element.TypeElement;
import java.util.List;
import java.util.stream.Collectors;

public class BuilderClass {

    private static final String BUILDER_NAME_PATTERN = "Builder";

    private final TypeElement type;

    private final List<Type> types;

    public BuilderClass(TypeElement type, List<Type> types) {
        this.type = type;
        this.types = types;
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
        final List<ParameterSpec> constructorParameters = types.stream()
                .map(Type::parameter)
                .collect(Collectors.toList());
        final List<CodeBlock> constructorStatements = types.stream()
                .map(Type::statement)
                .collect(Collectors.toList());

        return MethodSpec.constructorBuilder()
                .addModifiers(Modifier.PRIVATE)
                .addParameters(constructorParameters)
                .addStatement(CodeBlock.join(constructorStatements, ";\n")).build();
    }

    private MethodSpec buildMethod() {
        final String constructionParameters = types.stream()
                .map(Type::identity)
                .collect(Collectors.joining(", "));
        return MethodSpec.methodBuilder("build")
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get(type))
                .addStatement("return new $N(" + constructionParameters + ")", type.getSimpleName())
                .build();
    }

    private List<FieldSpec> builderFields() {
        return types.stream().map(Type::field).collect(Collectors.toList());
    }

    private List<MethodSpec> builderSetters() {
        return types.stream()
                .map(field -> field.setter(BUILDER_NAME_PATTERN))
                .collect(Collectors.toList());
    }
}
