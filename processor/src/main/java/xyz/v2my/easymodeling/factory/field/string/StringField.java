package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.CharSequenceField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.string.StringRandomizer;

public class StringField extends CharSequenceField {

    public StringField() {
    }

    protected StringField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    public StringField create(TypeName type, FieldWrapper field) {
        return new StringField(type, field);
    }

    @Override
    protected Class<? extends Randomizer<?>> randomizer() {
        return StringRandomizer.class;
    }
}
