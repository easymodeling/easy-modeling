package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldPattern;
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
    public DateField create(FieldPattern field, ModelField... valueFields) {
        return new DateField(field);
    }

    private DateField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<Date>> initializerType() {
        return DateRandomizer.class;
    }
}
