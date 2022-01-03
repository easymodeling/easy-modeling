package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

import java.util.List;

public abstract class Container<T> extends ModelField {

    protected List<ModelField> nestedFields;

    protected Container() {
    }

    protected Container(TypeName type, FieldWrapper field, List<ModelField> nestedFields) {
        super(type, field);
        this.nestedFields = nestedFields;
    }

    public abstract Container<T> create(TypeName type, FieldWrapper field, List<ModelField> nestedFields);

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $L<>($L, $L)", initializerType(), elementRandomizer(), randomParameter());
    }

    protected abstract CodeBlock initializerType();

    protected CodeBlock elementRandomizer() {
        return nestedFields.stream()
                .map(ModelField::initializer)
                .map(init -> CodeBlock.of("$L", init))
                .collect(CodeBlock.joining(", "));
    }

    protected abstract CodeBlock randomParameter();

}
