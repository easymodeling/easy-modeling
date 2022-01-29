package xyz.v2my.easymodeling.modeler;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeSpec;
import xyz.v2my.easymodeling.modeler.field.ModelField;

import javax.lang.model.element.Modifier;
import java.util.List;
import java.util.stream.Collectors;

import static xyz.v2my.easymodeling.GenerationPatterns.BUILDER_CLASS_NAME;

public class BuilderGenerator {

    private final List<ModelField> builderFields;

    private final ClassName returnType;

    public BuilderGenerator(List<ModelField> builderFields, ClassName builtTypeName) {
        this.builderFields = builderFields;
        this.returnType = builtTypeName;
    }

    public TypeSpec createType() {
        final TypeSpec.Builder builder = TypeSpec.classBuilder(BUILDER_CLASS_NAME)
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
                .map(ModelField::setter)
                .collect(Collectors.toList());
    }
}
