package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.LocalTimeRandomizer;

import java.time.LocalTime;

public class LocalTimeField extends AbstractDateTimeField<LocalTime> {

    public static final ClassName TYPE = ClassName.get(LocalTime.class);

    public LocalTimeField() {
        this.type = TYPE;
    }

    @Override
    public LocalTimeField create(FieldWrapper field, ModelField... valueFields) {
        return new LocalTimeField(field);
    }

    private LocalTimeField(FieldWrapper field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<LocalTime>> initializerType() {
        return LocalTimeRandomizer.class;
    }
}
