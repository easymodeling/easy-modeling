package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public abstract class InitializableContainerField extends ModelField {

    private InitializableField valueField;

    public InitializableContainerField() {
    }

    public InitializableContainerField(TypeName type, FieldWrapper field, InitializableField valueField) {
        super(type, field);
        this.valueField = valueField;
    }

    @Override
    public CodeBlock initialValue() {
        return CodeBlock.of("$L.next()", initializer());
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $L<>($L, $L)", initializerType(), elementRandomizer(), randomParameter());
    }

    protected abstract CodeBlock initializerType();

    private CodeBlock elementRandomizer() {
        return CodeBlock.of("$L", valueField.initializer());
    }

    protected abstract CodeBlock randomParameter();
}
