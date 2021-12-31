package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.string.StringRandomizer;

public class StringField extends CharSequenceField<String> {

    public StringField() {
    }

    private StringField(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public ModelField<String> create(TypeName type, FieldWrapper field) {
        return new StringField(type, field);
    }

    @Override
    protected Class<? extends Randomizer<String>> initializerType() {
        return StringRandomizer.class;
    }
}
