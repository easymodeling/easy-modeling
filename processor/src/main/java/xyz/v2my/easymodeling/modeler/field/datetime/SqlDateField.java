package xyz.v2my.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.datetime.SqlDateRandomizer;

import java.sql.Date;

public class SqlDateField extends AbstractDateTimeField<Date> {

    public static final ClassName TYPE = ClassName.get(Date.class);

    public SqlDateField() {
        this.type = TYPE;
    }

    @Override
    public SqlDateField create(FieldPattern field, ModelField... valueFields) {
        return new SqlDateField(field);
    }

    private SqlDateField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<Date>> initializerType() {
        return SqlDateRandomizer.class;
    }
}
