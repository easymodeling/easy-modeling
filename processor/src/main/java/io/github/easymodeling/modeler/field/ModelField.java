package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.ReflectionUtil;
import io.github.easymodeling.modeler.FieldCustomization;

import javax.lang.model.element.Modifier;

import static io.github.easymodeling.processor.GenerationPatterns.BUILDER_CLASS_NAME;

public abstract class ModelField implements Initializable, BuilderMember, StatementProvider {

    protected TypeName type;

    protected String name;

    // TODO: 06.05.22 use field.qualifiedName
    protected String qualifiedName;

    // TODO: 07.05.22 rename
    protected FieldCustomization field;

    protected boolean inherited;

    protected boolean hidden;

    protected ModelField() {
    }

    public abstract ModelField create(FieldCustomization field, ModelField... valueFields);

    // TODO: 08.05.22 rename `field` => `customization`
    protected ModelField(TypeName type, FieldCustomization field) {
        this.type = type;
        this.name = field.fieldName();
        this.qualifiedName = field.qualifiedName();
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
        return CodeBlock.of("this.$N = ($T) $T.getField(model, $S)", identity(), type(), ReflectionUtil.class, this.qualifiedName);
    }

    @Override
    public CodeBlock populateStatement() {
        return setFieldStatement(initialValue());
    }

    @Override
    public CodeBlock buildStatement() {
        return setFieldStatement(CodeBlock.of("$L", identity()));
    }

    private String identity() {
        return hidden ? qualifiedIdentity() : name;
    }

    private String qualifiedIdentity() {
        return qualifiedName.replace(".", "_").replace("#", "$");
    }

    public String fieldName() {
        return name;
    }

    public TypeName type() {
        return type;
    }

    public void setInherited(Boolean inherited) {
        this.inherited = inherited;
    }

    public boolean isInherited() {
        return inherited;
    }

    public void setHidden() {
        this.hidden = true;
    }

    public boolean isHidden() {
        return hidden;
    }

    private CodeBlock setFieldStatement(CodeBlock value) {
        return CodeBlock.of("$T.setField(model, $S, $L)", ReflectionUtil.class, this.qualifiedName, value);
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
