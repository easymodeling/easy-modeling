package xyz.v2my.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.PlainField;
import xyz.v2my.easymodeling.modeler.field.number.DoubleField;
import xyz.v2my.easymodeling.modeler.field.number.NumericField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.stream.DoubleStreamRandomizer;

import java.util.stream.DoubleStream;

public class DoubleStreamField extends PrimitiveTypeStreamField<DoubleStream, Double> {

    public static final ClassName TYPE = ClassName.get(DoubleStream.class);

    public DoubleStreamField() {
        type = TYPE;
    }

    @Override
    public PlainField<DoubleStream> create(FieldPattern field, ModelField... valueFields) {
        return new DoubleStreamField(field);
    }

    private DoubleStreamField(FieldPattern field) {
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
