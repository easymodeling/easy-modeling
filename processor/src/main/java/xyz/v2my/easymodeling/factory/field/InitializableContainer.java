package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import java.util.List;

public abstract class InitializableContainer extends Type {

    protected List<Type> nestedFields;

    public InitializableContainer() {
    }

    public InitializableContainer(TypeName type, FieldWrapper field, List<Type> nestedFields) {
        super(type, field);
        this.nestedFields = nestedFields;
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $L<>($L, $L)", initializerType(), elementRandomizer(), randomParameter());
    }

    protected abstract CodeBlock initializerType();

    protected CodeBlock elementRandomizer() {
        return nestedFields.stream()
                .map(Type::initializer)
                .map(init -> CodeBlock.of("$L", init))
                .collect(CodeBlock.joining(", "));
    }

    protected abstract CodeBlock randomParameter();
}
