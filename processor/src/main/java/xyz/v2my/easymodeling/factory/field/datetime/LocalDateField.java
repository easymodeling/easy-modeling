package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.LocalDateRandomizer;

import java.time.LocalDate;

public class LocalDateField extends AbstractDateTimeField<LocalDate> {

    public static final ClassName TYPE = ClassName.get(LocalDate.class);

    public LocalDateField() {
        this.type = TYPE;
    }

    @Override
    public LocalDateField create(FieldWrapper field, ModelField... valueFields) {
        return new LocalDateField(field);
    }

    private LocalDateField(FieldWrapper field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<LocalDate>> initializerType() {
        return LocalDateRandomizer.class;
    }
}
