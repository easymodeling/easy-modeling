package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.ParameterSpec;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import javax.lang.model.element.Modifier;
import java.util.concurrent.atomic.AtomicLong;

public abstract class ModelField implements InitializableType, BuilderMember, ConstructorContributor {

    private static final AtomicLong FIELD_COUNTER = new AtomicLong(0);

    protected TypeName type;

    protected String name;

    protected String identifier;

    protected FieldWrapper field;


    protected ModelField() {
    }

    protected ModelField(TypeName type, FieldWrapper field) {
        this.type = type;
        this.name = field.name();
        this.field = field;
        this.identifier = String.valueOf(FIELD_COUNTER.getAndIncrement());
    }

    @Override
    public FieldSpec field() {
        return FieldSpec.builder(type, identity(), Modifier.PRIVATE).build();
    }

    @Override
    public MethodSpec setter(String builderName) {
        return MethodSpec.methodBuilder(name)
                .addModifiers(Modifier.PUBLIC)
                .returns(ClassName.get("", builderName))
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
        return String.format("%s_%s", name, identifier);
    }

    public TypeName type() {
        return type;
    }
}
