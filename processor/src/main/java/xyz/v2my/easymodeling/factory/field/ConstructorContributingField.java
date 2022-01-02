package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class ConstructorContributingField extends AbstractField implements ConstructorContributor {

    protected ConstructorContributingField() {
    }

    protected ConstructorContributingField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public ParameterSpec parameter() {
        return ParameterSpec.builder(type, name).build();
    }

    @Override
    public CodeBlock statement() {
        return CodeBlock.of("this.$N = $N", name, name);
    }

}
