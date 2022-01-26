package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldPattern;

import javax.lang.model.element.Modifier;

import static xyz.v2my.easymodeling.GenerationPatterns.BUILDER_CLASS_NAME;

public abstract class ModelField implements InitializableType, BuilderMember, ConstructorContributor {

    protected TypeName type;

    protected String name;

    protected FieldPattern field;

    protected ModelField() {
    }

    public abstract ModelField create(FieldPattern field, ModelField... valueFields);

    protected ModelField(TypeName type, FieldPattern field) {
        this.type = type;
        this.name = field.name();
        this.field = field;
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("$L.next()", initializer());
    }

    @Override
    public FieldSpec field() {
        return FieldSpec.builder(type, identity(), Modifier.PRIVATE).build();
    }

    @Override
    public MethodSpec setter() {
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get("", BUILDER_CLASS_NAME))
                .addParameter(type, identity())
                .addStatement("this.$N = $N", identity(), identity())
                .addStatement("return this")
                .build();
    }

    @Override
    public ParameterSpec parameter() {
        return ParameterSpec.builder(type, identity()).build();
    }

    @Override
    public CodeBlock statement() {
        return CodeBlock.of("this.$N = $N", identity(), identity());
    }

    public String identity() {
        return name;
    }

    public TypeName type() {
        return type;
    }
}
