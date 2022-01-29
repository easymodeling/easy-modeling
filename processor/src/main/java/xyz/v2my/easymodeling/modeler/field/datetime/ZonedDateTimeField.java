package xyz.v2my.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.ZonedDateTimeRandomizer;

import java.time.ZonedDateTime;

public class ZonedDateTimeField extends AbstractDateTimeField<ZonedDateTime> {

    public static final ClassName TYPE = ClassName.get(ZonedDateTime.class);

    public ZonedDateTimeField() {
        this.type = TYPE;
    }

    @Override
    public ZonedDateTimeField create(FieldPattern field, ModelField... valueFields) {
        return new ZonedDateTimeField(field);
    }

    private ZonedDateTimeField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<ZonedDateTime>> initializerType() {
        return ZonedDateTimeRandomizer.class;
    }
}
