package xyz.v2my.easymodeling.factory.field.datetime;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.factory.FieldWrapper;
import xyz.v2my.easymodeling.factory.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.SqlTimestampRandomizer;

import java.sql.Timestamp;

public class SqlTimestampField extends AbstractDateTimeField<Timestamp> {

    public static final ClassName TYPE = ClassName.get(Timestamp.class);

    public SqlTimestampField() {
        this.type = TYPE;
    }

    @Override
    public SqlTimestampField create(FieldWrapper field, ModelField... valueFields) {
        return new SqlTimestampField(field);
    }

    private SqlTimestampField(FieldWrapper field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<Timestamp>> initializerType() {
        return SqlTimestampRandomizer.class;
    }
}
