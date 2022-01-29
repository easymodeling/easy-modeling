package xyz.v2my.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.DateRandomizer;

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
