package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;

import javax.lang.model.element.Modifier;

public abstract class AbstractField implements ModelField {

    protected final TypeName type;

    protected final String name;

    protected final FieldWrapper field;

    public AbstractField(TypeName type, FieldWrapper field) {
        this.type = type;
        this.name = field.name();
        this.field = field;
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

    protected abstract Class<? extends Randomizer> randomizer();

}
