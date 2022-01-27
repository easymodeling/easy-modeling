package xyz.v2my.easymodeling.factory.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldPattern;

import static xyz.v2my.easymodeling.GenerationPatterns.FACTORY_CLASS_NAME_PATTERN;

public class CustomerField extends ModelField {

    public CustomerField(TypeName type, FieldPattern field) {
        super(type, field);
    }

    @Override
    public CodeBlock initializer() {
        final String factoryTypeName = String.format(FACTORY_CLASS_NAME_PATTERN, type);

        return CodeBlock.builder().add("new $L()", factoryTypeName).build();
    }

    @Override
    public ModelField create(FieldPattern field, ModelField... valueFields) {
        throw new UnsupportedOperationException("Create CustomerField with constructor");
    }
}
