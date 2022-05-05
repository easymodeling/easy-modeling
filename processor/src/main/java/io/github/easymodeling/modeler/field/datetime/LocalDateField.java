package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.datetime.LocalDateRandomizer;

import java.time.LocalDate;

public class LocalDateField extends AbstractDateTimeField<LocalDate> {

    public static final ClassName TYPE = ClassName.get(LocalDate.class);

    public LocalDateField() {
        this.type = TYPE;
    }

    @Override
    public LocalDateField create(FieldCustomization field, ModelField... valueFields) {
        return new LocalDateField(field);
    }

    private LocalDateField(FieldCustomization field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<LocalDate>> initializerType() {
        return LocalDateRandomizer.class;
    }
}
