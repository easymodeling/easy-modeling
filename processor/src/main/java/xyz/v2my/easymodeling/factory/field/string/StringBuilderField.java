package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.string.StringBuilderRandomizer;

public class StringBuilderField extends CharSequenceField<StringBuilder> {

    public static final ClassName TYPE = ClassName.get(StringBuilder.class);

    public StringBuilderField() {
        this.type = TYPE;
    }

    @Override
    public StringBuilderField create(FieldWrapper field, ModelField... valueFields) {
        return new StringBuilderField(field);
    }

    private StringBuilderField(FieldWrapper field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<StringBuilder>> initializerType() {
        return StringBuilderRandomizer.class;
    }
}
