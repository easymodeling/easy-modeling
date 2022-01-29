package xyz.v2my.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.InstantRandomizer;

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
