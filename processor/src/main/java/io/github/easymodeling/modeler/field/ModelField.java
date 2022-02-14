package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.ReflectionUtil;
import io.github.easymodeling.modeler.FieldPattern;

import javax.lang.model.element.Modifier;

import static io.github.easymodeling.GenerationPatterns.BUILDER_CLASS_NAME;

public abstract class ModelField implements Initializable, BuilderMember, StatementProvider {

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
    public CodeBlock constructorStatement() {
        return CodeBlock.of("this.$N = ($T) $T.getField(model, $S)", identity(), type(), ReflectionUtil.class, identity());
    }

    @Override
    public CodeBlock populateStatement() {
        return setFieldStatement(initialValue());
    }

    @Override
    public CodeBlock buildStatement() {
        return setFieldStatement(CodeBlock.of("$L", identity()));
    }

    public String identity() {
        return name;
    }

    public TypeName type() {
        return type;
    }

    private CodeBlock setFieldStatement(CodeBlock value) {
        return CodeBlock.of("$T.setField(model, $S, $L)", ReflectionUtil.class, identity(), value);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
