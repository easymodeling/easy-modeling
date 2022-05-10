package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.datetime.LocalTimeRandomizer;

import java.time.LocalTime;

public class LocalTimeField extends AbstractDateTimeField<LocalTime> {

    public static final ClassName TYPE = ClassName.get(LocalTime.class);

    public LocalTimeField() {
        this.type = TYPE;
    }

    @Override
    public LocalTimeField create(FieldCustomization customization, ModelField... valueFields) {
        return new LocalTimeField(customization);
    }

    private LocalTimeField(FieldCustomization customization) {
        super(TYPE, customization);
    }

    @Override
    protected Class<? extends Randomizer<LocalTime>> initializerType() {
        return LocalTimeRandomizer.class;
    }
}
