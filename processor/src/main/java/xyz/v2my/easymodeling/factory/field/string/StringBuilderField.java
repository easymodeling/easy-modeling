package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.CharSequenceField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.string.StringBuilderRandomizer;

public class StringBuilderField extends CharSequenceField {

    public StringBuilderField() {
    }

    protected StringBuilderField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public StringBuilderField create(TypeName type, FieldWrapper field) {
        return new StringBuilderField(type, field);
    }

    @Override
    protected Class<? extends Randomizer<?>> randomizer() {
        return StringBuilderRandomizer.class;
    }
}
