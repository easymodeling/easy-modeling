package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.ClassName;
import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.string.StringRandomizer;

public class StringField extends CharSequenceField<String> {

    public StringField(FieldWrapper field) {
        super(ClassName.get(String.class), field);
    }

    @Override
    protected Class<? extends Randomizer<String>> initializerType() {
        return StringRandomizer.class;
    }
}
