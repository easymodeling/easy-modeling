package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.datetime.LocalDateTimeRandomizer;

import java.time.LocalDateTime;

public class LocalDateTimeField extends AbstractDateTimeField<LocalDateTime> {

    public static final ClassName TYPE = ClassName.get(LocalDateTime.class);

    public LocalDateTimeField() {
        this.type = TYPE;
    }

    @Override
    public LocalDateTimeField create(FieldCustomization customization, ModelField... valueFields) {
        return new LocalDateTimeField(customization);
    }

    private LocalDateTimeField(FieldCustomization customization) {
        super(TYPE, customization);
    }

    @Override
    protected Class<? extends Randomizer<LocalDateTime>> initializerType() {
        return LocalDateTimeRandomizer.class;
    }
}
