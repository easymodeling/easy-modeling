package xyz.v2my.easymodeling.modeler.field.stream;

import com.squareup.javapoet.ClassName;
import xyz.v2my.easymodeling.modeler.FieldPattern;
import xyz.v2my.easymodeling.modeler.field.ModelField;
import xyz.v2my.easymodeling.modeler.field.PlainField;
import xyz.v2my.easymodeling.modeler.field.number.IntegerField;
import xyz.v2my.easymodeling.modeler.field.number.NumericField;
import xyz.v2my.easymodeling.randomizer.Randomizer;
import xyz.v2my.easymodeling.randomizer.stream.IntStreamRandomizer;

import java.util.stream.IntStream;

public class IntStreamField extends PrimitiveTypeStreamField<IntStream, Integer> {

    public static final ClassName TYPE = ClassName.get(IntStream.class);

    public IntStreamField() {
        type = TYPE;
    }

    @Override
    public PlainField<IntStream> create(FieldPattern field, ModelField... valueFields) {
        return new IntStreamField(field);
    }

    private IntStreamField(FieldPattern field) {
        super(TYPE, field);
    }

    @Override
    protected Class<? extends Randomizer<IntStream>> initializerType() {
        return IntStreamRandomizer.class;
    }

    @Override
    protected NumericField<Integer> element() {
        return new IntegerField();
    }
}