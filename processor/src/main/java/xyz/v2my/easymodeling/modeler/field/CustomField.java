package xyz.v2my.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.randomizer.CustomTypeRandomizer;

import static xyz.v2my.easymodeling.GenerationPatterns.MODELER_NAME_PATTERN;
import static xyz.v2my.easymodeling.GenerationPatterns.MODEL_CACHE_PARAMETER_NAME;

public class CustomField extends ModelField {

    public CustomField(TypeName type, FieldPattern field) {
        super(type, field);
    }

    @Override
    public CodeBlock initializer() {
        final String factoryTypeName = String.format(MODELER_NAME_PATTERN, type);
        return CodeBlock.of("new $T<>(new $L(), $L)", CustomTypeRandomizer.class, factoryTypeName, MODEL_CACHE_PARAMETER_NAME);
    }

    @Override
    public ModelField create(FieldPattern field, ModelField... valueFields) {
        throw new UnsupportedOperationException("Create CustomerField with constructor");
    }
}
