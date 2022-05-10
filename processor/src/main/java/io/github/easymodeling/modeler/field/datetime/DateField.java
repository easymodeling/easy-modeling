package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.datetime.DateRandomizer;

import java.util.Date;

public class DateField extends AbstractDateTimeField<Date> {

    public static final ClassName TYPE = ClassName.get(Date.class);

    public DateField() {
        this.type = TYPE;
    }

    @Override
    public DateField create(FieldCustomization customization, ModelField... valueFields) {
        return new DateField(customization);
    }

    private DateField(FieldCustomization customization) {
        super(TYPE, customization);
    }

    @Override
    protected Class<? extends Randomizer<Date>> initializerType() {
        return DateRandomizer.class;
    }
}
