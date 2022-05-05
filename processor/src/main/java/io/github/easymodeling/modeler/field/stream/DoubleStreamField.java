package io.github.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import io.github.easymodeling.modeler.FieldCustomization;
import io.github.easymodeling.modeler.field.ModelField;
import io.github.easymodeling.modeler.field.PlainField;
import io.github.easymodeling.modeler.field.number.DoubleField;
import io.github.easymodeling.modeler.field.number.NumericField;
import io.github.easymodeling.randomizer.Randomizer;
import io.github.easymodeling.randomizer.stream.DoubleStreamRandomizer;

import java.util.stream.DoubleStream;

public class DoubleStreamField extends PrimitiveTypeStreamField<DoubleStream, Double> {

    public static final ClassName TYPE = ClassName.get(DoubleStream.class);

    public DoubleStreamField() {
        type = TYPE;
    }

    @Override
    public PlainField<DoubleStream> create(FieldCustomization field, ModelField... valueFields) {
        return new DoubleStreamField(field);
    }

    private DoubleStreamField(FieldCustomization field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<DoubleStream>> initializerType() {
        return DoubleStreamRandomizer.class;
    }

    @Override
    protected NumericField<Double> element() {
        return new DoubleField();
    }
}
