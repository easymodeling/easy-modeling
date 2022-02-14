package io.github.easymodeling.modeler.field.string;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.string.StringBuilderRandomizer;

public class StringBuilderField extends CharSequenceField<StringBuilder> {

    public static final ClassName TYPE = ClassName.get(StringBuilder.class);

    public StringBuilderField() {
        this.type = TYPE;
    }

    @Override
    public StringBuilderField create(FieldPattern field, ModelField... valueFields) {
        return new StringBuilderField(field);
    }

    private StringBuilderField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<StringBuilder>> initializerType() {
        return StringBuilderRandomizer.class;
    }
}
