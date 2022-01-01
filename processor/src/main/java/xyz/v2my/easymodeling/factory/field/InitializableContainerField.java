package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import java.util.List;

public abstract class InitializableContainerField extends ModelField {

    private List<ModelField> valueFields;

    public InitializableContainerField() {
    }

    public InitializableContainerField(TypeName type, FieldWrapper field, List<ModelField> valueFields) {
        super(type, field);
        this.valueFields = valueFields;
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $L<>($L, $L)", initializerType(), elementRandomizer(), randomParameter());
    }

    protected abstract CodeBlock initializerType();

    private CodeBlock elementRandomizer() {
        return valueFields.stream()
                .map(ModelField::initializer)
                .map(init -> CodeBlock.of("$L", init))
                .collect(CodeBlock.joining(", "));
    }

    protected abstract CodeBlock randomParameter();
}
