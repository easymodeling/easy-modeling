package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.processor.GenerationPatterns;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.randomizer.CustomTypeRandomizer;

public class CustomField extends ModelField {

    public CustomField(TypeName type, FieldPattern field) {
        super(type, field);
    }

    @Override
    public CodeBlock initializer() {
        final String factoryTypeName = String.format(GenerationPatterns.MODELER_NAME_PATTERN, type);
        return CodeBlock.of("new $T<>(new $L(), $L)", CustomTypeRandomizer.class, factoryTypeName, GenerationPatterns.MODEL_CACHE_PARAMETER_NAME);
    }

    @Override
    public ModelField create(FieldPattern field, ModelField... valueFields) {
        throw new UnsupportedOperationException("Create CustomerField with constructor");
    }
}
