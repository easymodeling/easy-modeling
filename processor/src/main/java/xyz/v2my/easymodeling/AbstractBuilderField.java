package xyz.v2my.easymodeling;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;

import javax.lang.model.element.Element;
import javax.lang.model.element.Modifier;

public abstract class AbstractBuilderField implements BuilderField {

    protected final TypeName type;

    protected final String name;

    public AbstractBuilderField(TypeName type, String name) {
        this.type = type;
        this.name = name;
    }

    @Override
    public ParameterSpec constructorParameter() {
        return ParameterSpec.builder(type, name).build();
    }

    @Override
    public CodeBlock constructorStatement() {
        return CodeBlock.of("this.$N = $N", name, name);
    }

    @Override
    public String constructorVariable() {
        return name;
    }

    @Override
    public FieldSpec builderField() {
        return FieldSpec.builder(type, name, Modifier.PRIVATE).build();
    }

    @Override
    public MethodSpec builderSetter(String builderTypeName) {
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get("", builderTypeName))
                .addParameter(type, name)
                .addStatement("this.$N = $N", name, name)
                .addStatement("return this")
                .build();
    }
}
