package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import javax.lang.model.element.Modifier;

public abstract class BuilderField extends ConstructorContributingField implements BuilderMember {

    public BuilderField() {
    }

    protected BuilderField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public FieldSpec field() {
        return FieldSpec.builder(type, name, Modifier.PRIVATE).build();
    }

    @Override
    public MethodSpec setter(String builderTypeName) {
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get("", builderTypeName))
                .addParameter(type, name)
                .addStatement("this.$N = $N", name, name)
                .addStatement("return this")
                .build();
    }
}
