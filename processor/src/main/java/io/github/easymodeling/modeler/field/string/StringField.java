package io.github.easymodeling.modeler.field.string;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.string.StringRandomizer;

public class StringField extends CharSequenceField<String> {

    public static final ClassName TYPE = ClassName.get(String.class);

    public StringField() {
        this.type = TYPE;
    }

    @Override
    public StringField create(FieldCustomization field, ModelField... valueFields) {
        return new StringField(field);
    }

    private StringField(FieldCustomization field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<String>> initializerType() {
        return StringRandomizer.class;
    }
}
