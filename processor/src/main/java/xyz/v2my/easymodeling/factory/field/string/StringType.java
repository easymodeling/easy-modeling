package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainType;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.string.StringRandomizer;

public class StringType extends CharSequenceType<String> {

    public StringType() {
    }

    private StringType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public PlainType<String> create(TypeName type, FieldWrapper field) {
        return new StringType(type, field);
    }

    @Override
    protected Class<? extends Randomizer<String>> initializerType() {
        return StringRandomizer.class;
    }
}
