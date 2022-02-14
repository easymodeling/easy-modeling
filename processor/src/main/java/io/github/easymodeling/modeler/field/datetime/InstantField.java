package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.datetime.InstantRandomizer;

import java.time.Instant;

public class InstantField extends AbstractDateTimeField<Instant> {

    public static final ClassName TYPE = ClassName.get(Instant.class);

    public InstantField() {
        this.type = TYPE;
    }

    @Override
    public InstantField create(FieldPattern field, ModelField... valueFields) {
        return new InstantField(field);
    }

    private InstantField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<Instant>> initializerType() {
        return InstantRandomizer.class;
    }
}
