package xyz.v2my.easymodeling.modeler.field.string;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.string.StringRandomizer;

public class StringField extends CharSequenceField<String> {

    public static final ClassName TYPE = ClassName.get(String.class);

    public StringField() {
        this.type = TYPE;
    }

    @Override
    public StringField create(FieldPattern field, ModelField... valueFields) {
        return new StringField(field);
    }

    private StringField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<String>> initializerType() {
        return StringRandomizer.class;
    }
}
