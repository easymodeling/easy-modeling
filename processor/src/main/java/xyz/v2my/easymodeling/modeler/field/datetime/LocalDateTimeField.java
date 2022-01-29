package xyz.v2my.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.LocalDateTimeRandomizer;

import java.time.LocalDateTime;

public class LocalDateTimeField extends AbstractDateTimeField<LocalDateTime> {

    public static final ClassName TYPE = ClassName.get(LocalDateTime.class);

    public LocalDateTimeField() {
        this.type = TYPE;
    }

    @Override
    public LocalDateTimeField create(FieldPattern field, ModelField... valueFields) {
        return new LocalDateTimeField(field);
    }

    private LocalDateTimeField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<LocalDateTime>> initializerType() {
        return LocalDateTimeRandomizer.class;
    }
}
