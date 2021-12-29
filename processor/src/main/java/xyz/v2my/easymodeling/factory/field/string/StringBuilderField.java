package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;

public class StringBuilderField extends StringField {

    public StringBuilderField() {
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T($L)", StringBuilder.class, super.initializer());
    }

    protected StringBuilderField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public StringBuilderField create(TypeName type, FieldWrapper field) {
        return new StringBuilderField(type, field);
    }
}
