package io.github.easymodeling.modeler.field.datetime;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldCustomization;
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
    public SqlDateField create(FieldCustomization customization, ModelField... valueFields) {
        return new SqlDateField(customization);
    }

    private SqlDateField(FieldCustomization customization) {
        super(TYPE, customization);
    }

    @Override
    protected Class<? extends Randomizer<Date>> initializerType() {
        return SqlDateRandomizer.class;
    }
}
