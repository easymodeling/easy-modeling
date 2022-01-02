package xyz.v2my.easymodeling.factory.field.string;

import com.squareup.javapoet.TypeName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.PlainType;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.string.StringBuilderRandomizer;

public class StringBuilderType extends CharSequenceType<StringBuilder> {

    public StringBuilderType() {
    }

    private StringBuilderType(TypeName type, FieldWrapper field) {
        super(type, field);
    }

    @Override
    public PlainType<StringBuilder> create(TypeName type, FieldWrapper field) {
        return new StringBuilderType(type, field);
    }

    @Override
    protected Class<? extends Randomizer<StringBuilder>> initializerType() {
        return StringBuilderRandomizer.class;
    }
}
