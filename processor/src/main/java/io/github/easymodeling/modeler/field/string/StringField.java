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
    public StringField create(FieldCustomization customization, ModelField... valueFields) {
        return new StringField(customization);
    }

    private StringField(FieldCustomization customization) {
        super(TYPE, customization);
    }

    @Override
    protected Class<? extends Randomizer<String>> initializerType() {
        return StringRandomizer.class;
    }
}
