package io.github.easymodeling.modeler.field;

import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.TypeName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.randomizer.CustomTypeRandomizer;

import static io.github.easymodeling.modeler.GenerationPatterns.MODELER_NAME_PATTERN;
import static io.github.easymodeling.modeler.GenerationPatterns.MODEL_CACHE_PARAMETER_NAME;

public class CustomField extends ModelField {

    private final String modelerName;

    public CustomField(TypeName type, FieldCustomization customization) {
        super(type, customization);
        this.modelerName = String.format(MODELER_NAME_PATTERN, type);
    }

    public CustomField(TypeName type, String modelerName, FieldCustomization customization) {
        super(type, customization);
        this.modelerName = modelerName;
    }

    @Override
    public CodeBlock initializer() {
        return CodeBlock.of("new $T<>(new $L(), $L)", CustomTypeRandomizer.class, this.modelerName, MODEL_CACHE_PARAMETER_NAME);
    }

    @Override
    public ModelField create(FieldCustomization customization, ModelField... valueFields) {
        throw new UnsupportedOperationException("Create CustomerField with constructor");
    }
}
