package xyz.v2my.easymodeling;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

public class BuilderField {

    private final TypeName type;

    private final String name;

    public BuilderField(Element fieldElement) {
        this.type = ClassName.get(fieldElement.asType());
        this.name = fieldElement.getSimpleName().toString();
    }

    public ParameterSpec constructorParameter() {
        return ParameterSpec.builder(type, name).build();
    }

    public CodeBlock constructorStatement() {
        return CodeBlock.of("this.$N = $N", name, name);
    }

    public String constructorVariable() {
        return name;
    }

    public FieldSpec builderField() {
        return FieldSpec.builder(type, name, Modifier.PRIVATE).build();
    }

    public MethodSpec builderSetter(String builderTypeName) {
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get("", builderTypeName))
                .addParameter(type, name)
                .addStatement("this.$N = $N", name, name)
                .addStatement("return this")
                .build();
    }

    public String filedInitializer() {
        switch (type.toString()) {
            case "int":
            case "long":
                return "0";
            default:
                return "null";
        }
    }
}
