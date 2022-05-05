package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.datetime.ZonedDateTimeRandomizer;

import java.time.ZonedDateTime;

public class ZonedDateTimeField extends AbstractDateTimeField<ZonedDateTime> {

    public static final ClassName TYPE = ClassName.get(ZonedDateTime.class);

    public ZonedDateTimeField() {
        this.type = TYPE;
    }

    @Override
    public ZonedDateTimeField create(FieldCustomization field, ModelField... valueFields) {
        return new ZonedDateTimeField(field);
    }

    private ZonedDateTimeField(FieldCustomization field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<ZonedDateTime>> initializerType() {
        return ZonedDateTimeRandomizer.class;
    }
}
