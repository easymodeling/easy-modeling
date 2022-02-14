package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.datetime.SqlTimestampRandomizer;

import java.sql.Timestamp;

public class SqlTimestampField extends AbstractDateTimeField<Timestamp> {

    public static final ClassName TYPE = ClassName.get(Timestamp.class);

    public SqlTimestampField() {
        this.type = TYPE;
    }

    @Override
    public SqlTimestampField create(FieldPattern field, ModelField... valueFields) {
        return new SqlTimestampField(field);
    }

    private SqlTimestampField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<Timestamp>> initializerType() {
        return SqlTimestampRandomizer.class;
    }
}
