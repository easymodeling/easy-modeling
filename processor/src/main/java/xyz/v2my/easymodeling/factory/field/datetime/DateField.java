package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.DateRandomizer;

import java.util.Date;

public class DateField extends AbstractDateTimeField<Date> {

    public static final ClassName TYPE = ClassName.get(Date.class);

    public DateField() {
        this.type = TYPE;
    }

    @Override
    public DateField create(FieldWrapper field, ModelField... valueFields) {
        return new DateField(field);
    }

    private DateField(FieldWrapper field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<Date>> initializerType() {
        return DateRandomizer.class;
    }
}
