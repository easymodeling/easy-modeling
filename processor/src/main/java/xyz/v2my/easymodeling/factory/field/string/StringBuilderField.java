package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.string.StringBuilderRandomizer;

public class StringBuilderField extends CharSequenceField<StringBuilder> {

    public StringBuilderField() {
    }

    private StringBuilderField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public PlainField<StringBuilder> create(TypeName type, FieldWrapper field) {
        return new StringBuilderField(type, field);
    }

    @Override
    protected Class<? extends Randomizer<StringBuilder>> initializerType() {
        return StringBuilderRandomizer.class;
    }
}
