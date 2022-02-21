package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldPattern;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.datetime.SqlDateRandomizer;

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
